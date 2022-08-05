package com.example.model;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Entity
@Getter @Setter
public class Coin {	
	@Id    
	//@GeneratedValue(strategy = GenerationType.AUTO)
    private String currency;
	private String dollar;
	private String ctname;	
	private double exrate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Timestamp lasttime;	
}