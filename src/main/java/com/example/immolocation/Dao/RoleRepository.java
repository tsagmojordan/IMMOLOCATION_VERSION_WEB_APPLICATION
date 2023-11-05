package com.example.immolocation.Dao;

import com.example.immolocation.Model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

       @Query("select u from Role u where u.role=?1")
       public Role findByName(String role);

       @Override
       List<Role> findAll();
}
