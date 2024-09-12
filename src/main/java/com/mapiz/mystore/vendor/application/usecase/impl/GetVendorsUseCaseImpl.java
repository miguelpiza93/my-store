package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.GetVendorsUseCase;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetVendorsUseCaseImpl implements GetVendorsUseCase {

    private final VendorRepository vendorRepository;

    @Override
    public List<Vendor> get() {
        return vendorRepository.findAll();
    }
}
