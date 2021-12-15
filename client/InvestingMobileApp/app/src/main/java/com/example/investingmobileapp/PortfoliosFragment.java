package com.example.investingmobileapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.BondServices;
import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.RequestServices.StockServices;
import com.example.investingmobileapp.helpers.DatabaseHelper;
import com.example.investingmobileapp.interfaces.IGetInstrumentResponse;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.InstrumentModel;
import com.example.investingmobileapp.models.PortfolioModel;
import com.example.investingmobileapp.models.ReportModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PortfoliosFragment extends Fragment {

    public static ArrayList<PortfolioModel> portfolios = new ArrayList<PortfolioModel>();
    Button btnAddPort, btnCreateReport;
    String user_id;
    ArrayList<InstrumentModel> stocks;
    ArrayList<InstrumentModel> bonds;
    ArrayList<ReportModel> reportModels = new ArrayList<>();
    PortfolioAdapter.OnPortfolioClickListener stateClickListener;
    RecyclerView recyclerView;
    public PortfoliosFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_portfolios, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
        getPortfolios();
        syncPortfolio();
    }

    public void init(){
        btnAddPort = getView().findViewById(R.id.btnAddPortfolio);
        btnCreateReport = getView().findViewById(R.id.btnPdf);
        Bundle arguments = getActivity().getIntent().getExtras();
        user_id = arguments.get("user_id").toString();
        recyclerView = getView().findViewById(R.id.listPortfolios);
        stateClickListener = new PortfolioAdapter.OnPortfolioClickListener() {
            @Override
            public void OnPortfolioClick(PortfolioModel state, int position) {
                Intent intent = new Intent(getContext(), PortfolioActivityOverview.class);
                intent.putExtra("status", "client");
                intent.putExtra("portfolio_id", state.getId());
                intent.putExtra("goal", state.getGoal());
                intent.putExtra("years", state.getYears());
                startActivity(intent);
            }
        };
        btnAddPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PortfolioActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        btnCreateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdf();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void syncPortfolio(){
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        List<PortfolioModel> list = dbHelper.getPortfolios();
        for (PortfolioModel model: portfolios) {
            if (!list.contains(model)) {
                dbHelper.addPortfolio(model);
            }
        }
    }

    public void getPortfolios(){
        portfolios = new ArrayList<>();
        PortfolioServices service = new PortfolioServices(getContext());
        service.getPortfolios("client", user_id, new IPortfolioResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<PortfolioModel> portfolioModels) {
                setData(portfolioModels);
            }
        });
    }

    public void setData(ArrayList<PortfolioModel> portfolioModels) {
        portfolios = portfolioModels;
        PortfolioAdapter adapter = new PortfolioAdapter(getActivity(), portfolioModels, stateClickListener);
        recyclerView.setAdapter(adapter);
    }


    public void getData(int i){
        stocks = new ArrayList<>();
        bonds = new ArrayList<>();
        BondServices service = new BondServices(getActivity());
        service.getBonds("portfolio", portfolios.get(i).getId()+"", new IGetInstrumentResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                bonds = instrumentModels;
                stocks = new ArrayList<>();
                StockServices service = new StockServices(getActivity());
                service.getStocks("portfolio", portfolios.get(i).getId()+"", new IGetInstrumentResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                        stocks = instrumentModels;
                        reportModels.add(new ReportModel(portfolios.get(i).getGoal(), portfolios.get(i).getYears(), getStatForPortfolio().get(0), getStatForPortfolio().get(1)));
                    }
                });
            }
        });
    }

    public ArrayList<Float> getStatForPortfolio(){
        float sumBonds = 0;
        float sumStocks = 0;
        for (InstrumentModel temp: bonds) {
            sumBonds += temp.getPrice();
        }
        for (InstrumentModel temp: stocks) {
            sumStocks += temp.getPrice();
        }
        float overAllSum = sumBonds + sumStocks;
        float percentStocks = sumStocks/overAllSum*100;
        float percentBonds = sumBonds/overAllSum*100;
        ArrayList<Float> percents = new ArrayList<>();
        percents.add(percentStocks);
        percents.add(percentBonds);
        return percents;
    }

    public void createPdf() throws InterruptedException {
        for (int i = 0; i < portfolios.size(); i++) {
            getData(i);
        }
        Thread.sleep(1000);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(14);
        paint.setStyle(Paint.Style.FILL);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Report.pdf");
        PdfDocument pdfDocument = new PdfDocument();
        try (FileOutputStream out = new FileOutputStream(file)) {

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 800, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas c = page.getCanvas();
            for (int i = 0; i < reportModels.size(); i++) {
                int step = 130*i;
                paint.setColor(Color.BLACK);
                c.drawText("Портфель " + i, 20, 20+step, paint);
                c.drawText("Цель " + reportModels.get(i).getGoal(), 20, 40+step, paint);
                c.drawText("Срок " + reportModels.get(i).getYears(), 20, 60+step, paint);
                c.drawText("Процент акций " + reportModels.get(i).getPercentStocks(), 20, 80+step, paint);
                c.drawText("Процент облигаций " + reportModels.get(i).getPercentBonds(), 20, 100+step, paint);
                paint.setColor(Color.RED);
                c.drawText("Тип портфеля " + reportModels.get(i).getType(), 20, 120+step, paint);
                paint.setColor(Color.BLACK);
            }
            c.save();
            pdfDocument.finishPage(page);
            pdfDocument.writeTo(out);
            out.flush();
            pdfDocument.close();

            Toast.makeText(getActivity(), "Отчёт успешно сохранён в " + file.getPath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),  "Не удалось сохранить отчёт", Toast.LENGTH_SHORT).show();
        }
    }
}