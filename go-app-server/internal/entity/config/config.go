package config

import (
	"flag"
	"fmt"
	"gopkg.in/natefinch/lumberjack.v2"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"
	"log/slog"
	"os"
	"path/filepath"
	"sync"
	"time"

	"gopkg.in/yaml.v3"
)

type Config struct {
	Server struct {
		Name            string `yaml:"name"`
		Listen          string `yaml:"listen"`
		ShutdownTimeout int    `yaml:"shutdownTimeout"`
	}

	Log struct {
		Filename        string `yaml:"filename"`
		MaxSize         int    `yaml:"maxSize"`
		MaxAge          int    `yaml:"maxAge"`
		Level           string `yaml:"level"`
		ReportCaller    bool   `yaml:"reportCaller"`
		OutputToConsole bool   `yaml:"outputToConsole"`
	}

	DB struct {
		Dsn             string `yaml:"dsn"`
		User            string `yaml:"user"`
		Password        string `yaml:"password"`
		MaxOpenConns    int    `yaml:"maxOpenConns"`
		MaxIdleConns    int    `yaml:"maxIdleConns"`
		ConnMaxLifetime int    `yaml:"connMaxLifetime"`
		ConnMaxIdleTime int    `yaml:"connMaxIdleTime"`
	} `yaml:"db"`
}

const (
	defaultConfigPath = "./conf"
	defaultConfigFile = "config.yaml"
)

var (
	ConfigPath = defaultConfigPath
	Gconfig    *Config
	Gdb        *gorm.DB
	once       sync.Once
)

func Init() {
	once.Do(func() {
		if ConfigPath == defaultConfigPath {
			flag.StringVar(&ConfigPath, "configPath", defaultConfigPath, "`path` to config file")
			if !flag.Parsed() {
				flag.Parse()
			}
		}
		var err error
		fullConfigFilePath, err := filepath.Abs(filepath.Join(ConfigPath, defaultConfigFile))
		if err != nil {
			panic(err)
		}
		slog.Info("full config file path", "path", fullConfigFilePath)
		Gconfig, err = readConfigFile(fullConfigFilePath)
		if err != nil {
			panic(err)
		}
		password := Gconfig.DB.Password
		Gconfig.DB.Password = "*******"
		slog.Info("read config file successfully", "config", Gconfig)
		Gconfig.DB.Password = password

		// 初始化日志
		initLog()
		// 初始化DB
		initDB()
	})
}

// readConfigFile 从文件加载配置
func readConfigFile(filename string) (*Config, error) {
	data, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	var config Config
	err = yaml.Unmarshal(data, &config)
	if err != nil {
		return nil, err
	}
	return &config, nil
}

// 初始化日志
func initLog() {
	logConf := Gconfig.Log
	// 1、切割
	lumberjackWriter := &lumberjack.Logger{
		Filename:   logConf.Filename,
		MaxSize:    logConf.MaxSize,
		MaxAge:     logConf.MaxAge,
		MaxBackups: 0,
		LocalTime:  true,
	}

	// 2. 配置 HandlerOptions：关键开启 ReportCaller = true
	var level slog.Level
	if err := level.UnmarshalText([]byte(logConf.Level)); err != nil {
		slog.Warn("parse log level error", "err", err.Error())
		level = slog.LevelInfo
	}
	handlerOpts := &slog.HandlerOptions{
		Level:     level,                // 日志级别（Info 及以上）
		AddSource: logConf.ReportCaller, // 开启调用者信息输出（核心配置）
		ReplaceAttr: func(groups []string, a slog.Attr) slog.Attr {
			// 自定义时间格式，显示到毫秒
			if a.Key == slog.TimeKey {
				if t, ok := a.Value.Any().(time.Time); ok {
					a.Value = slog.StringValue(t.Format(time.DateTime + ".000"))
				}
			}
			return a
		},
	}

	// 3. 设置默认日志
	if logConf.OutputToConsole {
		consoleHandler := slog.NewTextHandler(os.Stdout, handlerOpts)
		slog.SetDefault(slog.New(consoleHandler))
	} else {
		textHandler := slog.NewTextHandler(lumberjackWriter, handlerOpts)
		slog.SetDefault(slog.New(textHandler))
	}
}

// https://github.com/go-sql-driver/mysql
// https://gorm.io/zh_CN/docs/connecting_to_the_database.html
func initDB() {
	dbConf := Gconfig.DB
	dsn := fmt.Sprintf(dbConf.Dsn, dbConf.User, dbConf.Password)
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{
		NamingStrategy: schema.NamingStrategy{
			// 驼峰表名，不加s
			SingularTable: true,
			TablePrefix:   "t_",
		},
	})
	if err != nil {
		slog.Error("init db error", "err", err.Error())
		panic(err)
	}
	// 添加连接池
	sqlDB, err := db.DB()
	if err != nil {
		slog.Error("init db errs", "err", err.Error())
		panic(err)
	}
	sqlDB.SetMaxIdleConns(dbConf.MaxIdleConns)
	sqlDB.SetConnMaxLifetime(time.Duration(dbConf.ConnMaxLifetime) * time.Second)
	sqlDB.SetConnMaxIdleTime(time.Duration(dbConf.ConnMaxIdleTime) * time.Second)
	sqlDB.SetMaxOpenConns(dbConf.MaxOpenConns)
	Gdb = db
}

func Shutdown() {
	if db, err := Gdb.DB(); nil == err {
		db.Close()
		slog.Info("closed db success")
	} else {
		slog.Error("close db error", "err", err.Error())
	}
}
