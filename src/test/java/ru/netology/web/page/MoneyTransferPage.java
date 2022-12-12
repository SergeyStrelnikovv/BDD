package ru.netology.web.page;

import com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public MoneyTransferPage() {
        transferButton.shouldBe(visible);
        cancelButton.shouldBe(visible);
    }

    public DashboardPage transferMoney(int amountTransfer, DataHelper.Card card) {
        amountField.setValue(String.valueOf(amountTransfer));
        fromField.setValue(card.getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void errorMessage() {
        error.shouldBe(visible);
    }
}
