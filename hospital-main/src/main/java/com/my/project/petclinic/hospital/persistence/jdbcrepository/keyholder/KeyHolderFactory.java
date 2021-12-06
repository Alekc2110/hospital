package com.my.project.petclinic.hospital.persistence.jdbcrepository.keyholder;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {
    KeyHolder newKeyHolder();
}
