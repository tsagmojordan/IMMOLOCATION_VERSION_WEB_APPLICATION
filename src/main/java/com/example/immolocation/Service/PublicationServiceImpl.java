package com.example.immolocation.Service;

import com.example.immolocation.Model.Proprietes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationServiceImpl implements IPublicationServices {

    @Autowired
    IProprietesServices iProprietesServices;
    @Autowired
    IBailleurServices iBailleurServices;


    @Override
    public List publier() {

        List<Proprietes> proprieteList=iProprietesServices.findAllFreePropriete();
    return proprieteList;
    }

}
