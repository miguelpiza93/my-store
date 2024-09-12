package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.GetVendorByIdUseCase;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import com.mapiz.mystore.vendor.domain.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GetVendorByIdUseCaseImpl implements GetVendorByIdUseCase {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor apply(Integer id) {
        return vendorRepository.findById(id).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
    }
}
