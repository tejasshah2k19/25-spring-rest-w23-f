package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {

	T data;
	String msg; 
	Integer code;
	
}
