package org.dyzeng.imagelib.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageInfoRepository extends CrudRepository<ImageInfo, Long> {
    List<ImageInfo> findAll();
}
