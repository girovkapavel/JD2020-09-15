package by.it.yemialyanava.jd02_02;

import java.util.Map;

class Buyer extends Thread implements IBuyer, IUseBasket {
    private boolean waiting;

    boolean pensioneer;

    Buyer(int number, boolean pensionerLiYa) {
        this.pensioneer = pensionerLiYa;
        this.setName("Buyer № " + number + (pensioneer ? ", pensioner" : ""));
        Supervisor.addBuyer();
        waiting = false;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    @Override
    public void run() {
        enterToMarket();
        takeBasket();
        chooseGoods();
        goToQueue();
        goOut();
        Supervisor.leaveBuyer;
    }

    @Override
    public void enterToMarket() {
        System.out.println(this + " enter to market");
    }

    @Override
    public void chooseGoods() {
        System.out.println(this + " started to choose goods");
        int timeout = Helper.getRandom(500, 2000);
        if (pensioneer) {
            Helper.timeout(timeout + 1500);
        }
        Helper.timeout(timeout);
        putGoodsToBasket();
        System.out.println(this + " finish to choose goods");
    }

    @Override
    public void goToQueue() {
        System.out.println(this + "  go to queue");
        synchronized (this) {
            waiting = true;
            QueueBuyers.add(this);
            while (waiting)
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
        System.out.println(this + " leave queue");
    }


    @Override
    public void goOut() {
        System.out.println(this + " go out from Market");
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void takeBasket() {
        System.out.println(this + " take a basket");
    }

    @Override
    public void putGoodsToBasket() {
        Map<String, Double> goods = Good.getGoods();
        Object[] keys = goods.keySet().toArray();
        int goodsInBasket = Helper.getRandom(1, 4);
        String[] resultKeys = new String[goodsInBasket];
        for (int i = 0; i < goodsInBasket; i++) {
            int randomGoodIndex = Helper.getRandom(0, keys.length - 1);
            String key = (String) keys[randomGoodIndex];
            resultKeys[i] = key;
            if (pensioneer) {
                Helper.timeout(500 + 1500);
            }
            Helper.timeout(500);
        }
        StringBuilder purches = new StringBuilder();
        for (String resultkey : resultKeys) {
            purches.append("\t").append(resultkey).append(" ").append(goods.get(resultkey)).append("\n");
        }
        System.out.println(this + " buy\n " + purches.toString());
    }
}
