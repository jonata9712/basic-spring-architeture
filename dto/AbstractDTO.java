package br.com.bernhoeft.meetings.dto;


import java.io.Serializable;

import javax.persistence.Inheritance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Inheritance
@Data
public class AbstractDTO implements Serializable {
	
	private Long id;

}
