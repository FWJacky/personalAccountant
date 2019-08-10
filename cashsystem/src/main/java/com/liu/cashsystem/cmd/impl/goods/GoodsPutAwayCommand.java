package com.liu.cashsystem.cmd.impl.goods;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Goods;

/**
 * @ClassName GoodsPutAwayCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:44
 * @Version 1.0
 **/

@CommandMeta(name="SJSP",desc="上架商品",group = "商品信息")
@AdminCommand
public class GoodsPutAwayCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("上架商品");
        printlnInfo("请输入商品名称：");
        String name = scanner.nextLine();
        printlnInfo("商品简介：");
        String introduce = scanner.nextLine();
        printlnInfo("商品库存：");
        int stock = Integer.parseInt(scanner.nextLine());
        printlnInfo("商品单位：个，包，箱，台...");
        String unit = scanner.nextLine();
        printlnInfo("请输入商品价格：单位（元）");
        int price = new Double(100*scanner.nextDouble()).intValue();
        printlnInfo("请输入商品折扣：75表示75折");
        int discount = scanner.nextInt();

        Goods goods = new Goods();
        goods.setName(name);
        goods.setIntroduce(introduce);
        goods.setStock(stock);
        goods.setUnit(unit);
        goods.setPrice(price);
        goods.setDiscount(discount);

        boolean effect = this.goodsService.putAwayGoods(goods);
        if(effect) {
            printlnInfo("上架成功");
        }else {
            printlnInfo("上架失败");
        }
    }
}
