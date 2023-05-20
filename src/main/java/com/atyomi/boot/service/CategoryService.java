package com.atyomi.boot.service;

import com.atyomi.boot.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<Category> {
    void removeById(Long id);
}
