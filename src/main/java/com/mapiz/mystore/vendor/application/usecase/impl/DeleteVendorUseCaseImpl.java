package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.DeleteVendorUseCase;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DeleteVendorUseCaseImpl implements DeleteVendorUseCase {

    private final VendorRepository vendorRepository;

    @Override
    public void accept(Integer integer) {
        vendorRepository.findById(integer).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        vendorRepository.deleteById(integer);
    }
}
