package com.mapiz.mystore.vendor.application.usecase.impl;

import com.mapiz.mystore.vendor.application.mapper.VendorMapper;
import com.mapiz.mystore.vendor.application.usecase.CreateVendorUseCase;
import com.mapiz.mystore.vendor.application.usecase.command.CreateVendorCommand;
import com.mapiz.mystore.vendor.domain.Vendor;
import com.mapiz.mystore.vendor.domain.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVendorUseCaseImpl implements CreateVendorUseCase {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor apply(CreateVendorCommand command) {
        var model = VendorMapper.INSTANCE.commandToModel(command);
        return vendorRepository.save(model);
    }
}
