package com.openclassrooms.chatpo.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileNotFoundException extends RuntimeException {
    private final String errorMsg;
}
