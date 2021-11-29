package com.my.project.petclinic.hospital.persistence.jdbcRepository.keyHolder;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {
    KeyHolder newKeyHolder();
}
