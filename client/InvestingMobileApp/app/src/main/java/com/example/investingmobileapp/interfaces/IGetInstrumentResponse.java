package com.example.investingmobileapp.interfaces;

import com.example.investingmobileapp.models.InstrumentModel;

import java.util.ArrayList;
import java.util.List;

public interface IGetInstrumentResponse {

    void onError(String message);

    void onResponse(ArrayList<InstrumentModel> instrumentModels);

}
