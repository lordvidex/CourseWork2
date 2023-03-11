package ru.itis.stockmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.stockmarket.dtos.*;
import ru.itis.stockmarket.services.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 25.05.2022
 * Time: 11:33 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final BankService bankService;
    private final ProductService productService;
    private final OrganizationService<OrganizationRequestDto, OrganizationResponseDto> organizationService;
    private final ContractService contractService;
    private final CountryService countryService;

    @GetMapping("/bank")
    String getBanks(ModelMap model) {
        List<BankResponseDto> response = bankService.getAllOrganizations();
        model.addAttribute("list", response);
        return "bank";
    }

    @GetMapping("/organization")
    String getOrganizations(ModelMap model,
                            @RequestParam(required = false) String country) {
        List<OrganizationResponseDto> response =
                country == null
                        ? organizationService.getAllOrganizations()
                        : organizationService.getOrganizationsFrom(country);
        model.addAttribute("list", response);
        model.addAttribute("country", country);
        model.addAttribute("countries", countryService.getAllCountryCodes());
        return "organization";
    }

    @GetMapping("/organization-ajax")
    @ResponseBody
    List<OrganizationResponseDto> getOrganizationsAjax(@RequestParam(required = false) String country) {
        return country == null
                ? organizationService.getAllOrganizations()
                : organizationService.getOrganizationsFrom(country);
    }

    @GetMapping("/product")
    String getProducts(ModelMap model,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "20") int size,
                       @RequestParam(required = false) String country) {
        PagedResponse<ProductResponseDto> response;
        if (country != null) {
            ProductFilterDto filter = ProductFilterDto.builder().country(country).page(page).size(size).build();
            response = productService.getProducts(filter);
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            response = productService.getAllProducts(pageable);
        }
        model.addAttribute("pagedResponse", response);
        model.addAttribute("country", country);
        model.addAttribute("countries", countryService.getAllCountryCodes());
        return "product";
    }

    @GetMapping("/contract")
    String getContracts(ModelMap model,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        PagedResponse<ContractResponseDto> response = this.contractService.getAllContracts(pageable);
        model.addAttribute("contracts", response);
        return "contract";
    }

    @GetMapping("/*")
    String defaultAdminPage() {
        return "redirect:/admin/bank";
    }
}
