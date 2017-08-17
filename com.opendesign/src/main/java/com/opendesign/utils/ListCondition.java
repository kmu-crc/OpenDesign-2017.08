/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Joldo
 * List Paging 정보를 담고 있을 유틸성 클래스.
 *
 */
public class ListCondition extends HashMap<String, Object> {
	
	/** */
	private static final long serialVersionUID = -680819894087621566L;
	
	private static final int DEFAULT_ITEMS_PER_PAGE = 10;
	private static final int DEFAULT_BLOCK_CAPACITY = 11;
	
	/**
	 * 검색조건들을 담고 있을 맵 
	 */
	private Map<String, Object> params;
	
	/**
	 * 블록 사이즈
	 */
	private int blockCapacity;
	
	/**
	 * 총 레코드 수
	 */
	private int totalRecord;
	
	/**
	 * 현재 페이지 인덱스
	 */
	private int currentIndex;
	
	/**
	 * 페이지당 가져올 레코드 수 
	 */
	private int pageSize = 10;	
	
	/**
	 * 총 페이지 수
	 */
	private int totalPage;	
	
	/**
	 * 총 블록 수
	 */
	private int totalBlock;
	
	/**
	 * 현재 블록 인덱스
	 */
	private int currentBlock =1;

	private int startIndex;
	
	private int endIndex;
		
	public ListCondition( int pageSize ){
		
		blockCapacity = DEFAULT_BLOCK_CAPACITY;
		params = new HashMap<String, Object>();
		
		this.pageSize = pageSize;
		put("pageSize", pageSize);
		
	}
	

	public void setBlockCapacity(int i){
		blockCapacity = i;
	}
	public int getBlockCapacity(){
		return blockCapacity ;
	}
	

	public void clear(){
		params.clear();
	}
	

//	public Object put(Object key, Object value) {
//		return params.put(key, value );
//	}
	
	public void put(String key, int value) {
		params.put(key, new Integer(value) );			
	}
	public void put(String key, String value) {
		params.put(key, value );			
	}
	
//	public void put(String key, String value){
//		value = value==null?"":value;
//		
//		String[] arr = new String[1];
//		arr[0] = value;
//		this.put(key, arr );
//	}
	
//	public void setPageSize(int pageSize){
//		this.pageSize = pageSize;
//	}
	
	public void setCurrentIndex(int currentIndex ){
		this.currentIndex = currentIndex;
		put("currentIndex", currentIndex );
	}

	
	public Object get(Object key){
		return params.get(key);
	}
    
	public String[] getKeys(){
		Set aSet = params.keySet();
		return (String[])(aSet.toArray( new String[ aSet.size() ]  ));
	}
    
	public Object getValue(String key){		
		if( key == null){
			return "";
		}
		return params.get(key);
	}   
	
	

	public int getCurrentIndex() {
		if( currentIndex <= 0 ){
			return 1;
		}
		return currentIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setItemsPerPage(int i) {
		pageSize = i;
	}
	

	
	
	public int getPageSize(){
		return pageSize;
	}
	
	

	public void setTotalPage(int i) {
		totalPage = i;
	}



	public int getCurrentBlock() {
		return currentBlock;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setPageInfo(int recordTotalCount) {

		/** 11.19 **/
		this.totalRecord = recordTotalCount;
		
		if( currentIndex <= 0 ){
			currentIndex = 1;
		}

		if( pageSize <= 0){
			pageSize = DEFAULT_ITEMS_PER_PAGE;
		}
		
		if(recordTotalCount > 0) {
			totalPage = (int)Math.ceil((double) recordTotalCount/(double)pageSize);
			if(totalPage == 0) totalPage = 1;

			int front = (blockCapacity/2);
			startIndex = currentIndex - front;
			startIndex = startIndex<=0 ? 1 : startIndex;
			
			int rear = (blockCapacity/2) ;
			endIndex = currentIndex + rear;
			endIndex = endIndex>totalPage ? totalPage : endIndex ;
			
		} else {
			totalBlock = 1;
			currentIndex = 1;
		}
		
		
		this.put("prevItemSize", getPageSize() * (getCurrentIndex()-1));
		this.put("pageSize", pageSize);
		
	}
	
	/**
	 * 1,2,3올림순일때 첫번호전의 번호
	 * @author hanchanghao 2015.10.23
	 * @return
	 */
	public int getBaseNumberAscend(){
		return ( (getCurrentIndex()-1)  * getPageSize() );
	}
	
	public int getBaseNumber(){
		return getTotalRecord() - ( (getCurrentIndex()-1)  * getPageSize() );
	}
	
	
	/**
	 * For Mssql Paging Query
	 * @return
	 */
//	public int getPrevItemSize(){
//		return getPageSize() * (getCurrentIndex()-1);
//	}

	public List getParameterSetList() {
		List list = new ArrayList();
		
  		String[] keys =  getKeys();		
		for(int i = 0; i < keys.length; i++) {
			System.out.println( keys[i] );
			if( "pageSize".equals(keys[i]) || "currentIndex".equals(keys[i]) || "prevItemSize".equals(keys[i]) ){
				continue;
			}
			
			
			Object values = getValue(keys[i]);
			
			Map map = new Hashtable();
			map.put("key", keys[i] );
			map.put("value", values );
			
			
			if( map.size() != 0 ) list.add( map );
		}
		return list;
	}
	
	
	public void putParamaters(Map parameterMap) {
		if( parameterMap != null ){
			Iterator keys = parameterMap.keySet().iterator();
			while ( keys.hasNext()  ) {
				String key = (String)keys.next();
				Object value = parameterMap.get(key);			
				put(key, (String)value);
			}
		} 
	}	

	public Map generatePagingInfo() {
		Map pagingInfo= new HashMap();   
    	pagingInfo.put("totalPage", new Integer(getTotalPage()) );
    	pagingInfo.put("pageSize", new Integer(getPageSize()) );
    	pagingInfo.put("currentIndex", new Integer(getCurrentIndex()) );
    	pagingInfo.put("startIndex", new Integer(getStartIndex()) );
    	pagingInfo.put("endIndex", new Integer(getEndIndex()) );    	
    	pagingInfo.put("blockCapacity", new Integer(getBlockCapacity()) );
    	pagingInfo.put("parameters", getParameterSetList() );   	
    	
		return pagingInfo;
	}

}
