package com.talde3.laudiosarean.Jolasak.Kruzigrama;

public class HitzPosizioa {

    int hasierakoErrenkada;
    int hasierakoZutabea;
    int amaierakoErrenkada;
    int amairerakoZutabea;

    HitzPosizioa(int hasierakoErrenkada, int hasierakoZutabea, int amaierakoErrenkada, int amairerakoZutabea) {
        this.hasierakoErrenkada = hasierakoErrenkada;
        this.hasierakoZutabea = hasierakoZutabea;
        this.amaierakoErrenkada = amaierakoErrenkada;
        this.amairerakoZutabea = amairerakoZutabea;
    }
    public boolean isMatch(int aukituHasierakoErrenkada, int aurkituHasierakoZutabea) {
        return aukituHasierakoErrenkada == hasierakoErrenkada && aurkituHasierakoZutabea == hasierakoZutabea;
    }
}
