package distributed.systems.fruit_month_price;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitMonthPriceRepository extends JpaRepository<FruitMonthPrice, Long> {
    FruitMonthPrice findByFruitAndMonth(String fruit, String month);
}
