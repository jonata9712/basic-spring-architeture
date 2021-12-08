package br.com.bernhoeft.meetings.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
public @Data class ErrorDTO implements Serializable {
	
	private String code;
	private String error;
	private String message;
	private String objectName;
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private String status;
	private List<ErrorDTO> errors;
	private List<ErrorObjectDTO> errorsObjects;

}

