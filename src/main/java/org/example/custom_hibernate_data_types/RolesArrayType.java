package org.example.custom_hibernate_data_types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

public class RolesArrayType implements UserType {
    private static final String DATA_TYPE = "roles";

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return String[].class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if(o instanceof String[] && o1 instanceof String[]){
            return Arrays.deepEquals((String[]) o, (String[]) o1);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Arrays.hashCode((String[]) o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings,
                              SharedSessionContractImplementor sharedSessionContractImplementor,
                              Object o) throws HibernateException, SQLException {
        Array array = resultSet.getArray(strings[0]);
        return array == null ? null : array.getArray();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException, SQLException {
        if(preparedStatement != null && o != null){
            Array array = sharedSessionContractImplementor.connection()
                    .createArrayOf(DATA_TYPE, (String[]) o);
            preparedStatement.setArray(i, array);
        } else{
            preparedStatement.setNull(i, sqlTypes()[0]);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        String[] array = (String[]) o;
        if(array != null) return Arrays.copyOf(array, array.length);
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
