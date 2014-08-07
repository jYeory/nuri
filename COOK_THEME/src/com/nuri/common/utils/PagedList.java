package com.nuri.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * =============================================================================
 *            프로젝트명 :   np_VerseOfDay
 *            화  일  명 :   PagedList.java
 *            기      능 :   List의 Paging 구현을 용이하게 하는 Collection class
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2010. 11. 17.      이재열<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
@DataTransferObject
public class PagedList<E> implements Serializable, List<E> {
	
	static final long serialVersionUID = 4456497502076278504L;

	/** 전체 데이터 수 */
	private int totalCount = 0;

	/** 결과 List */
	private List<E> dataList = new ArrayList<E>();

	/** 각종 정보를 저장하는 Map */
	private HashMap<Object, Object> values = new HashMap<Object, Object>();

	/** 만약 rownum과 같은 컬럼이 없을 경우 이를 이용하여 번호를 부여할 수 있다. */
	private int rownum = 0;

	private boolean isDesc = false;
	
	private int pageSize = 15;
	private int currentPage = 1;
	
	/**
	 * 특정 값 반환
	 * 
	 * @param chunk
	 * @return
	 */
	public static Object chunkValue(PagedList<?> chunk, Object key) {
		if(chunk==null) return null;
		return chunk.getValue(key);
	}
	
	/**
	 * 페이지 크기 반환
	 * 
	 * @param chunk
	 * @return
	 */
	public static int chunkPageSize(PagedList<?> chunk) {
		if(chunk==null) return 10;
		return chunk.getPageSize();
	}
	
	/**
	 * 전체 데이터수 반환
	 * 
	 * @param chunk
	 * @return
	 */
	public static int chunkTotalCount(PagedList<?> chunk) {
		if(chunk==null) return 0;
		return chunk.getTotalCount();
	}
	
	/**
	 * 현재 가진 데이터수 반환
	 * 
	 * @param lst
	 * @return
	 */
	public static int sizeOf(List<?> lst) {
		if(lst==null) return 0;
		return lst.size();
	}
	
	/**
	 * 현재 번호 반환
	 * 
	 * @param chunk
	 * @return
	 */
	public static int chunkRowNum(PagedList<?> chunk) {
		if(chunk==null) return 0;
		return chunk.getRownum();
	}

	/**
	 * Constructor
	 * 
	 * @param count
	 * @param data
	 */
	public PagedList(int count, List<E> data) {
		this.totalCount = count;
		if(data!=null) this.dataList = data;
	}
	
	/**
	 * Constructor 
	 * 
	 * @param data
	 */
	public PagedList(List<E> data) {
		if(data!=null) {
			this.dataList = data;
		}
	}
	
	/**
	 * Default Constructor
	 */
	public PagedList() {
		super();
	}

	/**
	 * 증가/감소 여부(isDesc)에 따라서 rownum을 가져올때
	 * 값을 1씩 증가/감소 시켜서 가져온다. 
	 * 
	 * @return int
	 */
	public int getRownum() {
		if(isDesc) return rownum--;
		return rownum++;
	}

	/**
	 * Sets the rownum. (Default로 DESC 정렬을 이용한다.)
	 * 
	 * @param rownum The rownum to set
	 */
	public void setRownum(int rownum) {
		setRownum(rownum, true);
	}

	/**
	 * Sets the rownum.
	 * 
	 * @param rownum 시작번호
	 * @param isDesc 번호의 증가/감소 여부
	 */
	public void setRownum(int rownum, boolean isDesc) {
		this.rownum = rownum;
		this.isDesc = isDesc;
	}
	
	/**
	 * return totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * set totalCount
	 */
	public void setTotalCount(int count) {
		this.totalCount = count;
	}
	
	/**
	 * return dataList
	 */
	public List<E> getDataList() {
		return dataList;
	}


	/**
	 * set dataList
	 */
	public void setDataList(List<E> data) {
		if(data==null) return;
		this.dataList = data;
	}

	/**
	 * get data count
	 */
	public int getDataCount() {
		return this.size();
	}

	/**
	 * return user define value
	 */
	public Object getValue(Object objKey) {
		if(values==null) return null;
		return values.get(objKey);
	}

	/**
	 * set user define value
	 */
	public void setValue(Object objKey, Object value) {
		if(values==null) return;
		this.values.put(objKey, value);
	}

	/**
	 * return all user define values
	 */
	public HashMap<?, ?> getAllValues() {
		return this.values;
	}
	
	/**
	 * JSON 문자열로 결과를 변환
	 * 
	 * @return
	 */
	public String toJSON() {
		return toJSON(null);
	}
	
	/**
	 * JSON 문자열로 excludes에 해당하는 값을 빼고 변환
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toJSON(String[] excludes) {
		HashMap map = new HashMap();
		map.put("total", ((totalCount-1)/pageSize)+1);   // 전체 페이지수
		map.put("records", getTotalCount()==0?this.size():getTotalCount()); // 전체 레코드수
		map.put("page", currentPage);        // 현재 페이지
		map.put("pagesize", getPageSize()); // 페이지당 레코드수
		map.put("canNext", currentPage< ((totalCount-1)/pageSize)+1);
		map.put("canPrevious", 1<currentPage);
		map.put("userdata", getAllValues()); // 사용자정의 값
		
		map.put("rows", getDataList()); // 목록
		
		return JSONUtil.toJSON(map, excludes);
	}

	/**
	 * @see java.util.List#size()
	 */
	public int size() {
		return dataList.size();
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return dataList.isEmpty();
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return dataList.contains(o);
	}

	/**
	 * @see java.util.List#iterator()
	 */
	public Iterator<E> iterator() {
		return dataList.iterator();
	}

	/**
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return dataList.toArray();
	}

	/*
	 **
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] a) {
		return dataList.toArray(a);
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(E o) {
		return dataList.add(o);
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return dataList.remove(o);
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return dataList.containsAll(c);
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends E> c) {
		return dataList.addAll(c);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		return dataList.addAll(index, c);
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return dataList.removeAll(c);
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return dataList.retainAll(c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear() {
		this.dataList.clear();
	}

	/**
	 * @see java.util.List#get(int)
	 */
	public E get(int index) {
		return dataList.get(index);
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public E set(int index, E element) {
		return dataList.set(index, element);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, E element) {
		this.dataList.add(index, element);
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	public E remove(int index) {
		return this.dataList.remove(index);
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return dataList.indexOf(o);
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return dataList.lastIndexOf(o);
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<E> listIterator() {
		return dataList.listIterator();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<E> listIterator(int index) {
		return dataList.listIterator(index);
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	public List<E> subList(int fromIndex, int toIndex) {
		return dataList.subList(fromIndex, toIndex);
	}


	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param ps
	 */
	public void setPageSize(int ps) {
		pageSize = ps;
	}
	
	/**
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * @param pg
	 */
	public void setCurrentPage(int pg) {
		currentPage = pg;
	}

	/**
	 * @return
	 */
	public boolean isDesc(){
		return isDesc;
	}

	public Map<?, ?> getPagedInfos(){
		values.put("rows", dataList);
		values.put("page", getCurrentPage());
		values.put("pagesize", getPageSize());
		values.put("records", getTotalCount());
		values.put("total", (getTotalCount()-1)/getPageSize() + 1);
		return values;
	}
}
