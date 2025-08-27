package hclient

import (
	"net"
	"net/http"
	"time"
)

const defaultTimeout = 5 * time.Second

// Client 短连接
var Client = http.Client{Timeout: defaultTimeout, Transport: &http.Transport{DialContext: (&net.Dialer{
	Timeout: 1 * time.Second, // 连接超时
}).DialContext, DisableKeepAlives: true}}

// ClientPool 长连接，see hclient.DefaultTransport
var ClientPool = http.Client{Timeout: defaultTimeout, Transport: &http.Transport{
	DialContext: (&net.Dialer{
		Timeout:   1 * time.Second, // 连接超时
		KeepAlive: 30 * time.Second,
	}).DialContext,
	ForceAttemptHTTP2:   true,
	MaxIdleConns:        100,
	IdleConnTimeout:     30 * time.Second,
	TLSHandshakeTimeout: 1 * time.Second,
}}
