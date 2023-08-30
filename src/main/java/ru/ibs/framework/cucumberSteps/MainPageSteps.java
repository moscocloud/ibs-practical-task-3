package ru.ibs.framework.cucumberSteps;

import io.cucumber.java.ru.И;
import ru.ibs.framework.UI.managers.PageManager;
import ru.ibs.framework.UI.pages.MainPage;

public class MainPageSteps {

    PageManager pageManager = PageManager.getInstance();

    @И("^Проверка открытия сайта по заголовку$")
    public void checkOpenPage() {
        pageManager.getPage(MainPage.class).checkOpenPage();
    }

    @И("^Проверка наличия таблицы товаров$")
    public void checkTableDisplayed(){
        pageManager.getPage(MainPage.class).checkTableDisplayed();
    }

    @И("^Проверка заголовков таблицы товаров$")
    public void checkTitlesTables(){
        pageManager.getPage(MainPage.class).checkTitlesTables();
    }

    @И("^Проверка строки таблицы с параметрами: Наименование - \"(.+)\", Тип - \"(.+)\", Экзотический - \"(.+)\"$")
    public void checkTableRowWithParamForCucumber(String name, String type, String exotic){
        pageManager.getPage(MainPage.class).checkTableRowWithParamForCucumber(name, type, exotic);
    }

    @И("^Проверка кнопки \"Добавить\"$")
    public void checkButtonAdd() {
        pageManager.getPage(MainPage.class).checkButtonAdd();
    }

    @И("^Нажатие на кнопку \"Добавить\"$")
    public void clickButtonAdd() {
        pageManager.getPage(MainPage.class).clickButtonAdd();
    }
}
