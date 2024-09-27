package com.mapiz.mystore.shared;

public record ApiError(String error, String message, int status) {}
