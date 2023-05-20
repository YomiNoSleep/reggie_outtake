package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.AddressBook;
import com.atyomi.boot.mapper.AddressBookMapper;
import com.atyomi.boot.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService{
}
