package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    void shouldTransferZeroMoneyBetweenOwnCards() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val moneyTransferPage = dashboardPage.secondCard();
        int amount = 0;
        moneyTransferPage.transferMoney(amount, DataHelper.FirstCard());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBefore - amount), balanceFirstCardAfter);
        assertEquals((balanceSecondCardBefore + amount), balanceSecondCardAfter);
    }

    @Test
    void shouldTransferMoneyBetweenFirstCardToSecondCard() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCard();
        int amount = 10000;
        transferPage.transferMoney(amount, DataHelper.FirstCard());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBefore - amount), balanceFirstCardAfter);
        assertEquals((balanceSecondCardBefore + amount), balanceSecondCardAfter);
    }

    @Test
    void shouldTransferMoneyBetweenSecondCardToFirstCard() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCard();
        int amount = 1000;
        transferPage.transferMoney(amount, DataHelper.SecondCard());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBefore + amount), balanceFirstCardAfter);
        assertEquals((balanceSecondCardBefore - amount), balanceSecondCardAfter);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsMoreThanHave() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCard();
        int amount = 100000;
        transferPage.transferMoney(amount, DataHelper.FirstCard());
        transferPage.errorMessage();
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCardBefore, balanceFirstCardAfter);
        assertEquals(balanceSecondCardBefore, balanceSecondCardAfter);
    }
}