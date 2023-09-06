package com.productprice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Notification {
	private String category;
	private int code;
	private String description;
}
