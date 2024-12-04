package org.misis.tp.ttf.lab5;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DamageHandlerTest {

    @Test
    @DisplayName("Тестирует базовую структуру абстрактного обработчика")
    public void AbstractDamageHandlerTest() {
        AbstractDamageHandler testAbstractDamageHandler = new AbstractDamageHandler();
        int testDamage = 0;

        int actualDamage = testAbstractDamageHandler.handle(testDamage);

        Assertions.assertEquals(testDamage, actualDamage);

        AbstractDamageHandler testNextAbstractDamageHandlerMock = Mockito.mock(AbstractDamageHandler.class);
        int testHandledDamage = 10;
        when(testNextAbstractDamageHandlerMock.handle(testDamage)).thenReturn(testHandledDamage);

        testAbstractDamageHandler.setNext(testNextAbstractDamageHandlerMock);
        actualDamage = testAbstractDamageHandler.handle(testDamage);

        Assertions.assertEquals(testHandledDamage, actualDamage);
        verify(testNextAbstractDamageHandlerMock, times(1)).handle(testDamage);
    }

    @Test
    @DisplayName("Тестирует обнуление урона обработчиком неуязвимости")
    public void InvulnerabilityDamageHandlerTest() {
        InvulnerabilityDamageHandler invulnerabilityDamageHandler = new InvulnerabilityDamageHandler();
        int testDamage = 100;
        int zero = 0;

        int actualDamage = invulnerabilityDamageHandler.handle(testDamage);

        Assertions.assertEquals(zero, actualDamage);

        AbstractDamageHandler mockHandler = Mockito.mock(AbstractDamageHandler.class);
        invulnerabilityDamageHandler.setNext(mockHandler);

        actualDamage = invulnerabilityDamageHandler.handle(testDamage);

        Assertions.assertEquals(zero, actualDamage);
        verify(mockHandler, never()).handle(anyInt());
    }

    @Test
    public void BarrierDamageHandlerTest() {
        int testBarrierHealth = 100;
        BarrierDamageHandler barrierDamageHandler = new BarrierDamageHandler(testBarrierHealth);

        int doubleTestBarrierHealth = testBarrierHealth * 2;
        int halfOfDealtDamage = doubleTestBarrierHealth - testBarrierHealth;

        int actualDamage = barrierDamageHandler.handle(doubleTestBarrierHealth);

        Assertions.assertEquals(halfOfDealtDamage, actualDamage);

        barrierDamageHandler = new BarrierDamageHandler(testBarrierHealth);
        AbstractDamageHandler mockHandler = Mockito.mock(AbstractDamageHandler.class);
        barrierDamageHandler.setNext(mockHandler);
        when(mockHandler.handle(halfOfDealtDamage)).thenReturn(halfOfDealtDamage);

        actualDamage = barrierDamageHandler.handle(doubleTestBarrierHealth);

        Assertions.assertEquals(halfOfDealtDamage, actualDamage);
        verify(mockHandler, times(1)).handle(halfOfDealtDamage);

        int halfOfTestBarrierHealth = testBarrierHealth / 2;
        int zero = 0;
        barrierDamageHandler = new BarrierDamageHandler(testBarrierHealth);

        int actualDamageFirst = barrierDamageHandler.handle(halfOfTestBarrierHealth);
        int actualDamageSecond = barrierDamageHandler.handle(halfOfTestBarrierHealth);
        int actualDamageThird = barrierDamageHandler.handle(halfOfTestBarrierHealth);

        Assertions.assertEquals(zero, actualDamageFirst);
        Assertions.assertEquals(zero, actualDamageSecond);
        Assertions.assertEquals(halfOfTestBarrierHealth, actualDamageThird);
    }

    @Test
    public void BuffDebuffDamageHandlerTest() {
        int testDamage = 100;

        float testBuffMultiplier = 0.3f;
        BuffDebuffDamageHandler buffDamageHandler = new BuffDebuffDamageHandler(testBuffMultiplier);
        int expectedBuffDebuffHandledDamage = Math.round(testDamage * testBuffMultiplier);

        int actualUnderBuffDamage = buffDamageHandler.handle(testDamage);

        Assertions.assertEquals(expectedBuffDebuffHandledDamage, actualUnderBuffDamage);

        float testDebuffMultiplier = 1.3f;
        BuffDebuffDamageHandler debuffDamageHandler = new BuffDebuffDamageHandler(testDebuffMultiplier);
        expectedBuffDebuffHandledDamage = Math.round(testDamage * testDebuffMultiplier);

        int actualUnderDebuffDamage = debuffDamageHandler.handle(testDamage);

        Assertions.assertEquals(expectedBuffDebuffHandledDamage, actualUnderDebuffDamage);

        AbstractDamageHandler mockHandler = Mockito.mock(AbstractDamageHandler.class);
        debuffDamageHandler.setNext(mockHandler);
        when(mockHandler.handle(expectedBuffDebuffHandledDamage)).thenReturn(testDamage);

        int actualDamage = debuffDamageHandler.handle(testDamage);

        Assertions.assertEquals(testDamage, actualDamage);
        verify(mockHandler, times(1)).handle(expectedBuffDebuffHandledDamage);
    }

}