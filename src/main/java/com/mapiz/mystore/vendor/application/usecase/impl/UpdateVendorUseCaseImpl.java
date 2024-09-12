package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.usecase.UpdateVendorUseCase;
import com.mapiz.mystore.vendor.application.usecase.command.UpdateVendorCommand;
import com.mapiz.mystore.vendor.application.exception.VendorNotFoundException;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UpdateVendorUseCaseImpl implements UpdateVendorUseCase {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor apply(UpdateVendorCommand command) {
        var vendor = vendorRepository.findById(command.getId())
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        vendor.setName(command.getName());
        return vendorRepository.save(vendor);
    }
}
