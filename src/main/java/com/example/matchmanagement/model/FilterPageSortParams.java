package com.example.matchmanagement.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterPageSortParams {


	private Integer page;
	private Integer limit;
	
	private String sortBy;
	private String sortOrder;
}
