package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "topics")
public interface TopicClient {
}
