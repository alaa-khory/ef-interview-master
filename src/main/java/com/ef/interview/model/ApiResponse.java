package com.ef.interview.model;


public record ApiResponse<T>(boolean success, String message, T data) { }
