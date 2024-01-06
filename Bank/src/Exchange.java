public class Exchange {
    private final static double CNY_TO_USD_RATE = 0.143728;
    private final static double GBP_TO_USD_RATE = 1.22731;
    private final static double INR_TO_USD_RATE = 0.0121338;

    public static double exchangeCurrency(CurrencyType type1, CurrencyType type2, double exchangeAmount){
        switch (type1){
            case CNY:
                return exchangeCNYTo(type2,exchangeAmount);
            case GBP:
                return exchangeGBPTo(type2, exchangeAmount);
            case INR:
                return exchangeINRTo(type2, exchangeAmount);
            case USD:
                return exchangeUSDTo(type2, exchangeAmount);
            default:
                return 0;
        }
    }

    private static double exchangeCNYTo(CurrencyType currencyType, double exchangeAmount){
        switch (currencyType){
            case USD:
                return exchangeAmount * CNY_TO_USD_RATE;
            case INR:
                return exchangeUSDTo(CurrencyType.INR, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case GBP:
                return exchangeUSDTo(CurrencyType.GBP, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case CNY:
                return exchangeAmount;
            default:
                return 0;
        }
    }

    private static double exchangeGBPTo(CurrencyType currencyType, double exchangeAmount){
        switch (currencyType){
            case USD:
                return exchangeAmount * GBP_TO_USD_RATE;
            case INR:
                return exchangeUSDTo(CurrencyType.INR, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case CNY:
                return exchangeUSDTo(CurrencyType.CNY, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case GBP:
                return exchangeAmount;
            default:
                return 0;
        }
    }

    private static double exchangeINRTo(CurrencyType currencyType, double exchangeAmount){
        switch (currencyType){
            case USD:
                return exchangeAmount * INR_TO_USD_RATE;
            case CNY:
                return exchangeUSDTo(CurrencyType.CNY, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case GBP:
                return exchangeUSDTo(CurrencyType.GBP, exchangeCNYTo(CurrencyType.USD, exchangeAmount));
            case INR:
                return exchangeAmount;
            default:
                return 0;
        }
    }

    private static double exchangeUSDTo(CurrencyType currencyType, double exchangeAmount){
        switch (currencyType){
            case CNY:
                return exchangeAmount / CNY_TO_USD_RATE;
            case INR:
                return exchangeAmount / INR_TO_USD_RATE;
            case GBP:
                return exchangeAmount / GBP_TO_USD_RATE;
            case USD:
                return exchangeAmount;
            default:
                return 0;
        }
    }
}
