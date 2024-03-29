package com.evmoter.test.model;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AddressType implements UserType ,ParameterizedType{

	    public static final String LIST_TYPE = "LIST";
	    private static final int[] SQL_TYPES = new int[]{Types.JAVA_OBJECT};
	    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	    private static final TypeReference LIST_TYPE_REF = new TypeReference<List<?>>() {};

	    private JavaType valueType = null;
	    private Class<?> classType = null;

	    @Override
	    public int[] sqlTypes() {
	        return SQL_TYPES;
	    }

	    @Override
	    public Class<?> returnedClass() {
	        return classType;
	    }

	    @Override
	    public boolean equals(Object x, Object y) throws HibernateException {   
	        return Objects.equals(x, y);
	    }

	    @Override
	    public int hashCode(Object x) throws HibernateException {
	        return Objects.hashCode(x);
	    }

	    @Override
	    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException,     SQLException {
	        return nullSafeGet(rs, names, owner);
	    }

	    @Override
	    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
	        nullSafeSet(st, value, index);
	    }

	    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
	        String value = rs.getString(names[0]);
	        Object result = null;
	        if (valueType == null) {
	            throw new HibernateException("Value type not set.");
	        }
	        if (value != null && !value.equals("")) {
	            try {
	                result = OBJECT_MAPPER.readValue(value, valueType);
	            } catch (IOException e) {
	                throw new HibernateException("Exception deserializing value " + value, e);
	            }
	        }
	        return result;
	    }

	    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
	        StringWriter sw = new StringWriter();
	        if (value == null) {
	            st.setNull(index, Types.VARCHAR);
	        } else {
	            try {
	                OBJECT_MAPPER.writeValue(sw, value);
	               // st.setString(index, sw.toString());
	                st.setObject(index, sw.toString(), Types.OTHER);
	            } catch (IOException e) {
	                throw new HibernateException("Exception serializing value " + value, e);
	            }
	        }
	    }

	    @Override
	    public Object deepCopy(Object value) throws HibernateException {
	        if (value == null) {
	            return null;
	        } else if (valueType.isCollectionLikeType()) {
	            try {
	                Object newValue = value.getClass().newInstance();
	                Collection newValueCollection = (Collection) newValue;
	                newValueCollection.addAll((Collection) value);
	                return newValueCollection;
	            } catch (InstantiationException e) {
	                throw new HibernateException("Failed to deep copy the collection-like value object.", e);
	            } catch (IllegalAccessException e) {
	                throw new HibernateException("Failed to deep copy the collection-like value object.", e);
	            }
	        }
			return value;
	    }

	    @Override
	    public boolean isMutable() {
	        return true;
	    }

	    @Override
	    public Serializable disassemble(Object value) throws HibernateException {
	         return (Serializable) deepCopy(value);
	    }

	    @Override
	    public Object assemble(Serializable cached, Object owner) throws HibernateException {
	        return deepCopy(cached);
	    }

	    @Override
	    public Object replace(Object original, Object target, Object owner) throws HibernateException {
	        return deepCopy(original);
	    }

	    @Override
	    public void setParameterValues(Properties parameters) {
	        String type = parameters.getProperty("type");
	        if (type.equals(LIST_TYPE)) {
	            if (parameters.getProperty("element") != null) {
	                try {
	                    valueType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, Class.forName(parameters.getProperty("element")));
	                } catch (ClassNotFoundException e) {
	                     throw new IllegalArgumentException("Type " + type + " is not a valid type.");
	                }
	            } else {
	                valueType = OBJECT_MAPPER.getTypeFactory().constructType(LIST_TYPE_REF);
	            }
	            classType = List.class;
	        } 
}
}