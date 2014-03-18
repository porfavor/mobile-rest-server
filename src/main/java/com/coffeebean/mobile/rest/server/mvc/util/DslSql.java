package com.coffeebean.mobile.rest.server.mvc.util;

import org.apache.commons.lang3.StringUtils;

import com.esotericsoftware.reflectasm.FieldAccess;

public class DslSql {
	
	 	private static final String prefix_tabel = "";
	
		private FieldAccess fieldAccess;
	
		private Object o;
		
	    private StringBuilder sql = new StringBuilder();
	    
	    private StringBuilder where = new StringBuilder(" where 1=1");
	    
	    private String table = "";
	    
	    
	    private String underscoreName(String name) {
			/*StringBuilder result = new StringBuilder();
			if (name != null && name.length() > 0) {
				result.append(name.substring(0, 1).toLowerCase());
				for (int i = 1; i < name.length(); i++) {
					String s = name.substring(i, i + 1);
					if (s.equals(s.toUpperCase())) {
						result.append("_");
						result.append(s.toLowerCase());
					}
					else {
						result.append(s);
					}
				}
			}
			return result.toString();*/
			
			StringBuilder result = new StringBuilder();
			
			for(char letter : name.toCharArray()){
				if (Character.isUpperCase(letter)) {
					result.append("_");
					result.append(String.valueOf(letter).toLowerCase());
				} else {
					result.append(letter);
				}
			}
			
			return result.toString();
		}
	    
	    public DslSql(){}
	    
	    public DslSql (Object o){
	    	this.fieldAccess = FieldAccess.get(o.getClass());
	    	String className = o.getClass().getSimpleName();
	    	this.table = underscoreName(StringUtils.uncapitalize(className));
	    	this.o = o;
	    }
	    
	    private String[] cp(String[] source,String... excludeFields){
	    	int len = source.length;
	    	String[] target = new String[len];
	    	for(int i=0;i<len;i++){
	    		target[i] = source[i];
	    	}
	    	for(int i=0;i<len;i++){
	    		for(String exclude:excludeFields){
	    			if(exclude.equals(target[i])){
	    				target[i] = null;
	    			}
	    		}
	    	}
	    	return target;
	    }
	    
	    private void calUpdate(String ...excludeFields){
	    	String[] target = cp(fieldAccess.getFieldNames(),excludeFields);
	    	for(String fieldName:target){
	    		if(fieldName!=null){
	    			Object oo = fieldAccess.get(o, fieldName);
					if(!BizUtils.isEmpty(oo)){
						sql.append("`"+underscoreName(fieldName)+"`=:"+fieldName+",");
					}
	    		}
	    	}
	    }
	    
	    private void calInsert(String ...excludeFields){
	    	String[] target = cp(fieldAccess.getFieldNames(),excludeFields);
	    	StringBuilder value = new StringBuilder(" values(");
	    	for(String fieldName:target){
	    		if(fieldName!=null){
	    			sql.append("`"+underscoreName(fieldName)+"`,");
	    			value.append(":"+fieldName+",");
	    		}
	    	}
	    	sql.delete(sql.length()-1, sql.length());
	    	value.delete(value.length()-1, value.length());
	    	sql.append(")").append(value.append(")"));
	    }
	    
	    public DslSql update(String...excludeFields){
	    	sql.append("update "+prefix_tabel+table+" set ");
	    	calUpdate(excludeFields);
	    	sql.delete(sql.length()-1, sql.length());
	    	return this;
	    }
	    
	    public DslSql insert(String...excludeFields){
	    	sql.append("insert into "+prefix_tabel+table+"(");
	    	calInsert(excludeFields);
	    	return this;
	    }
	    
	    public DslSql select(){
	    	sql.append("select * from "+prefix_tabel+table);
	    	return this;
	    }
	    
	    public DslSql select(String select){
	    	sql.append(" "+underscoreName(select));
	    	return this;
	    }
	    
	    public DslSql innerJoin(String table){
	    	sql.append(" inner join "+underscoreName(table));
	    	return this;
	    }
	    
	    public DslSql leftJoin(String table){
	    	sql.append(" left join "+underscoreName(table));
	    	return this;
	    }
	    
	    public DslSql on(String on){
	    	sql.append(" on "+underscoreName(on));
	    	return this;
	    }
	    
	    public DslSql delete(){
	    	sql.append("delete from "+prefix_tabel+table);
	    	return this;
	    }
	    
	    public DslSql count(){
	    	sql.append("select count(*) from "+prefix_tabel+table);
	    	return this;
	    }
	    
	    public DslSql count(String count){
	    	sql.append(underscoreName(count));
	    	return this;
	    }
	    
	    public DslSql where(String...fields){
	    	for(String field:fields){
	    		String[] f = field.split("\\.");
	    		String _field = f.length>1?f[1]:field;
	    		String columnName = f.length>1?(f[0]+".`"+underscoreName(f[1])+"`"):"`"+underscoreName(field)+"`";
				Object oo = fieldAccess.get(o, _field);
				if(!BizUtils.isEmpty(oo)){
					where.append(" and "+columnName+"=:"+_field);
				}
			}
	    	sql.append(where);
	    	return this;
	    }
	    
	    public DslSql or(String...fields){
	    	boolean dynamicWhere = false;
	    	StringBuilder or = new StringBuilder(" and (");
	    	for(String field:fields){
				Object oo = fieldAccess.get(o, field);
				if(!BizUtils.isEmpty(oo)){
					dynamicWhere = true;
					or.append("`"+underscoreName(field)+"`=:"+field+" or ");
				}
			}
	    	if(dynamicWhere){
	    		int len = or.length();
		    	or.delete(len-4, len);
		    	or.append(")");
		    	sql.append(or);
	    	}
	    	return this;
	    }
	    
	    public DslSql like(String...fields){
	    	boolean dynamicWhere = false;
	    	StringBuilder or = new StringBuilder(" and (");
	    	for(String field:fields){
				Object oo = fieldAccess.get(o, field);
				if(!BizUtils.isEmpty(oo)){
					dynamicWhere = true;
					or.append("`"+underscoreName(field)+"` like :"+field+" or ");
				}
			}
	    	if(dynamicWhere){
	    		int len = or.length();
		    	or.delete(len-4, len);
		    	or.append(")");
		    	sql.append(or);
	    	}
	    	return this;
	    }
	    
	public DslSql likeAs(String column, String field) {
		boolean dynamicWhere = false;
		StringBuilder or = new StringBuilder(" and ");
		Object oo = fieldAccess.get(o, field);
		if (!BizUtils.isEmpty(oo)) {
			dynamicWhere = true;
			or.append("" + column + " like :" + field + " ");
		}
		if (dynamicWhere) {
			sql.append(or);
		}
		return this;
	}
	
	public DslSql equalAs2(String column1, String field1, String column2, String field2) {
		boolean dynamicWhere = false;
		StringBuilder or = new StringBuilder(" and ( ");
		Object oo1 = fieldAccess.get(o, field1);
		Object oo2 = fieldAccess.get(o, field2);
		if (!BizUtils.isEmpty(oo1) && !BizUtils.isEmpty(oo2)) {
			dynamicWhere = true;
			or.append("" + column1 + " = :" + field1 + " ");
			or.append("or ");
			or.append("" + column2 + " = :" + field2 + " ");
		}
		
		or.append(" ) ");
		
		if (dynamicWhere) {
			sql.append(or);
		}
		
		return this;
	}

	/**
	 */
	public DslSql likeAs2(String column1, String field1, String column2, String field2) {
		boolean dynamicWhere = false;
		StringBuilder or = new StringBuilder(" and ( ");
		Object oo1 = fieldAccess.get(o, field1);
		Object oo2 = fieldAccess.get(o, field2);
		if (!BizUtils.isEmpty(oo1) && !BizUtils.isEmpty(oo2)) {
			dynamicWhere = true;
			or.append("" + column1 + " like :" + field1 + " ");
			or.append("or ");
			or.append("" + column2 + " like :" + field2 + " ");
		}
		
		or.append(" ) ");
		
		if (dynamicWhere) {
			sql.append(or);
		}
		
		return this;
	}
	    
	    public DslSql andIn(String column,String field){
	    	sql.append(" and `" + underscoreName(column) +"` in (:" + field+")");
	    	return this;
	    }
	    
	    public DslSql andInnerSql(String column,String field){
	    	String[] f = column.split("\\.");
    		String columnName = f.length>1?(f[0]+".`"+underscoreName(f[1])+"`"):"`"+underscoreName(column)+"`";
	    	
	    	sql.append(" and " + columnName +" in (" + field+")");
	    	return this;
	    }
	    
	    public DslSql andNotInSql(String column,String field){
	    	sql.append(" and " + underscoreName(column) +" not in (" + field+")");
	    	return this;
	    }
	    
	    public DslSql and(String column,String field){
	    	sql.append(" and `" + underscoreName(column) +"`=:" + field);
	    	return this;
	    }
	    public DslSql andltEq(String column,String field){
	    	sql.append(" and " + column +"<=:" + field);
	    	return this;
	    }
	    
	    public DslSql andStaticWhereSql(String field,String innerSql){
	    	sql.append(" and " + field +"=" + innerSql);
	    	return this;
	    }
	    
	    public DslSql orginRange(String field,String startField,String endField) {
	    	
			if(!BizUtils.isEmpty(startField)&&!BizUtils.isEmpty(endField)){
				sql.append(" and ( " + field + ">=:" + startField);
	    		sql.append(" and  " + field+ "<:" + endField+" )");
			}
	    	return this;
	    }
	    
	    
	    public DslSql having(String field,String startField,String endField) {
	    	
			if(!BizUtils.isEmpty(startField)&&!BizUtils.isEmpty(endField)){
				sql.append(" having  " + field + ">=:" + startField);
	    		sql.append(" and  " + field+ "<:" + endField+" ");
			}
	    	return this;
	    }

		public DslSql rangeAs(String field,String startField,String endField) {
	    	
	    	Object _startField = fieldAccess.get(o, startField);
	    	Object _endField = fieldAccess.get(o, endField);
			if(!BizUtils.isEmpty(_startField)&&!BizUtils.isEmpty(_endField)){
				sql.append(" and ( " + field + ">=:" + startField);
	    		sql.append(" and  " + field + "<:" + endField+" )");
			}
	    	return this;
	    }
	    
	    public DslSql range(String field,String startField,String endField) {
	    	
	    	Object _startField = fieldAccess.get(o, startField);
	    	Object _endField = fieldAccess.get(o, endField);
			if(!BizUtils.isEmpty(_startField)&&!BizUtils.isEmpty(_endField)){
				sql.append(" and ( `" + underscoreName(field) + "`>=:" + startField);
	    		sql.append(" and  `" + underscoreName(field) + "`<:" + endField+" )");
			}
	    	return this;
	    }
	    
	    public DslSql notEq(String field,String value){
	    	sql.append(" and  " + field + "!=" + value+" ");
	    	return this;
	    }
	    
	    public DslSql groupBy(String groupBy){
	    	sql.append(" group by "+groupBy);
	    	return this;
	    }
	    
	    public DslSql orderBy(String orderBy){
	    	sql.append(" order by "+underscoreName(orderBy));
	    	return this;
	    }
	    
	    public DslSql limit(){
	    	sql.append(" limit :start, :limit");
	    	return this;
	    }
	    
	    public String toSql(){
	    	return sql.toString();
	    }
	    
	    public static void main(String[] args) {
		}
	    
}
