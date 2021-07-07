package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;


interface ConfigurationItemRepository extends JpaRepository<ConfigurationItem, Long> {

}