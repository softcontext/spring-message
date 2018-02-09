package com.example.demo.activemq.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
	private static final long serialVersionUID = -7167392094112819467L;
	private String from;
	private String to;
	private BigDecimal amount;
	private LocalDateTime timestamp;
}
