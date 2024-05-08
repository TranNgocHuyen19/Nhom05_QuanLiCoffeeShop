/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author pc
 */
public interface BaseDAO<T> {
    public List<T> findAll();
    public T findById(String id);
    public boolean insert(T t);
    public boolean update(T t);
    public boolean delete(String id);
}
