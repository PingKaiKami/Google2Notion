from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
import time


driver = webdriver.Chrome()
driver.get('https://accounts.google.com/')

driver.implicitly_wait(5)

email = driver.find_element(By.XPATH, '//*[@id="identifierId"]')
email.send_keys('F74121220@gs.ncku.edu.tw')

button = driver.find_element(By.XPATH, '//*[@id="identifierNext"]/div/button')
button.click()

password = driver.find_element(By.XPATH, '//*[@id="password"]/div[1]/div/div[1]/input')
password.send_keys('Aa30367082')

button = driver.find_element(By.XPATH, '//*[@id="passwordNext"]/div/button/span')
button.click()
time.sleep(2)

driver.get("https://www.google.com/maps")
time.sleep(2)

button = driver.find_element(By.XPATH, '//*[@id="omnibox-singlebox"]/div/div[1]/button')
button.click()

button = driver.find_element(By.XPATH, '//*[@id="settings"]/div/div[2]/ul/div[4]/li[1]/button')
button.click()

button = driver.find_element(By.XPATH, '//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[2]/button')
button.click()

scrollable_div = driver.find_element(By.XPATH, '//div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde ussYcc "]')

# 滾動視窗加載更多內容的函數
def scroll_div(element):
    last_height = driver.execute_script("return arguments[0].scrollHeight", element)
    while True:
        # 滾動視窗到底部
        driver.execute_script("arguments[0].scrollTop = arguments[0].scrollHeight", element)
        # 等待頁面加載
        time.sleep(2)  # 可根據需要調整等待時間
        # 記錄新的高度
        new_height = driver.execute_script("return arguments[0].scrollHeight", element)
        # 檢查是否已經滾動到視窗底部
        if new_height == last_height:
            break
        last_height = new_height

# 滾動視窗來加載所有內容
scroll_div(scrollable_div)

while True:
    mode = input("請輸入搜尋模式(0:簡略搜尋所有店家, 1:詳細搜尋特定店家, 2:詳細搜尋所有店家, 88:結束搜尋):")

    if mode == '0':
        restaurants = driver.find_elements(By.XPATH, '//div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde ussYcc "]/div[@class="m6QErb XiKgde "]')
        print("=====================================")
        i=0
        for restaurant in restaurants:
            try:
                name = restaurant.find_element(By.XPATH, './/div[@class="fontHeadlineSmall rZF81c "]').text
                # 讀取評分
                rating = restaurant.find_element(By.XPATH, './/span[@class="MW4etd"]').text
                # 讀取評論數
                review_count = restaurant.find_element(By.XPATH, './/span[@class="UY7F9"]').text
                
                print("餐廳名稱:", name)
                print("評分:", rating)
                print("評論數:", review_count)
                print("=====================================")
                i+=1
            except:
                break

    elif mode == '1':
        driver.implicitly_wait(0.5)
        restaurants = driver.find_elements(By.XPATH,'//div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde ussYcc "]/div[@class="m6QErb XiKgde "]')
        search = input("請輸入要詳細搜尋的餐廳名稱:")
        getRestaurant = False
        for restaurant in restaurants:
            name = restaurant.find_element(By.XPATH, './/div[@class="fontHeadlineSmall rZF81c "]').text
            if search in name:
                restaurant.click()
                getRestaurant = True
                break

        if not getRestaurant:
            print("找不到", search)

        time.sleep(0.5)

        if getRestaurant:
            try:
                services = driver.find_elements(By.XPATH, '//div[@class="LTs0Rc"]')
                for service in services:
                    service_type = service.get_attribute("aria-label")
                    print(service_type)
            except:
                print("網頁無提供服務類型")

            try:
                business_hours_element = driver.find_element(By.XPATH, '//div[@class="t39EBf GUrTXd"]')
                business_hours = business_hours_element.get_attribute("aria-label")
                print("營業時間:", business_hours)
                businesshour = True
            except:
                businesshour = False
            if not businesshour:
                try:
                    button = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-item-id="oh"]')
                    button.click()
                    time.sleep(0.5)
                    business_hours_element = driver.find_element(By.XPATH, '//div[@class="t39EBf GUrTXd"]')
                    business_hours = business_hours_element.get_attribute("aria-label")
                    print("營業時間:", business_hours)
                    businesshour = True
                    time.sleep(0.5)
                    back_window = driver.find_element(By.XPATH, '//button[contains(@class, "VfPpkd-icon-LgbsSe yHy1rc eT1oJ mN1ivc") and @aria-label="返回"]')
                    back_window.click()
                except:
                    businesshour = False
            if not businesshour:
                print("無營業時間")

            try:
                address_element = driver.find_element(By.XPATH, '//a[@class="CsEnBe" and @data-tooltip="預訂"]')
                address = address_element.get_attribute("href")
                print("外送平台網站:", address)
                fooddilivery = True
            except:
                fooddilivery = False

            if not fooddilivery:
                try:
                    address_element = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-tooltip="預訂"]')
                    address_element.click()
                    time.sleep(0.5)
                    window = driver.find_element(By.XPATH, '//div[@class="m6QErb XiKgde "]')
                    address_elements = window.find_elements(By.XPATH, './/a[@class="CsEnBe"]')
                    for address_element in address_elements:
                        address = address_element.get_attribute("href")
                        print("外送平台網站:", address)
                    fooddilivery = True
                    time.sleep(0.5)
                    close_window = window.find_element(By.XPATH, '//button[@class="omsYrc"]')
                    close_window.click()
                except:
                    fooddilivery = False
            if not fooddilivery:
                print("無外送平台網站")

            try:
                address_element = driver.find_element(By.XPATH, '//a[@class="CsEnBe" and @data-tooltip="開啟網站"]')
                address = address_element.get_attribute("href")
                print("粉專網站:", address)
            except:
                print("無粉專網站")

            try:
                phone_element = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-tooltip="複製電話號碼"]')
                phone = phone_element.get_attribute("aria-label")
                print(phone)
            except:
                print("無電話號碼")
            try:
                scrollable_div = driver.find_element(By.XPATH, '//div[@class="m6QErb WNBkOb XiKgde "]/div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde "]')
                scroll_div(scrollable_div)
                button = driver.find_element(By.XPATH, '//div[@class="m6QErb Hk4XGb QoaCgb XiKgde KoSBEe tLjsW "]//div[@class=""]/button[@class="M77dve "]')
                button.click()
                time.sleep(0.5)
                # 讀取標籤
                tags = driver.find_elements(By.XPATH, '//div[contains(@class, "KNfEk") or contains(@class, "KNfEk lpkcdc")]')
                for tag in tags:
                    tag_name = tag.find_element(By.XPATH, './/button[@class="e2moi "]').get_attribute("aria-label")
                    print(tag_name)
                # 讀取評論
                comments = driver.find_elements(By.XPATH, '//div[@class="m6QErb XiKgde "]/div[@class="jftiEf fontBodyMedium "]')
                i=1
                for comment in comments:
                    shop_star = comment.find_element(By.XPATH, './/span[@class="kvMYJc"]').get_attribute("aria-label")
                    upload_time = comment.find_element(By.XPATH, './/span[@class="rsqaWe"]').text
                    try:
                        button = comment.find_element(By.XPATH, './/button[@class="w8nwRe kyuRq"]')
                        button.click()
                        time.sleep(0.5)
                    except:
                        pass
                    comment_text = comment.find_element(By.XPATH, './/div[@class="MyEned"]/span[@class="wiI7pd"]').text
                    print("評論", i ,":")
                    print(shop_star, upload_time)
                    print(comment_text)
                    i+=1
            except:
                print("評論不足")
            print("=====================================")
            time.sleep(0.5)
    
    elif mode == '2':
        with open('data.txt', 'w', encoding='utf-8') as file:
            driver.implicitly_wait(0.3)
            restaurants = driver.find_elements(By.XPATH, '//div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde ussYcc "]/div[@class="m6QErb XiKgde "]')
            i=0
            for restaurant in restaurants:
                i+=1
                # 讀取餐廳名稱
                name = restaurant.find_element(By.XPATH, './/div[@class="fontHeadlineSmall rZF81c "]').text
                # 讀取評分
                rating = restaurant.find_element(By.XPATH, './/span[@class="MW4etd"]').text
                # 讀取評論數
                review_count = restaurant.find_element(By.XPATH, './/span[@class="UY7F9"]').text
                # 讀取餐廳類型
                shop = restaurant.find_element(By.XPATH, './/div[@class="IIrLbb"][2]').text

                file.write(f"餐廳名稱: {name}\n")
                file.write(f"評分: {rating}\n")
                file.write(f"評論數: {review_count}\n")
                file.write(f"餐廳類型: {shop}\n")
                restaurant.click()
                time.sleep(0.5)
                try:
                    # 讀取服務類型
                    services = driver.find_elements(By.XPATH, '//div[@class="LTs0Rc"]')
                    for service in services:
                        service_type = service.get_attribute("aria-label")
                        file.write(service_type + "\n")
                except:
                    file.write("網頁無提供服務類型\n")

                try:
                    # 讀取營業時間
                    business_hours_element = driver.find_element(By.XPATH, '//div[@class="t39EBf GUrTXd"]')
                    business_hours = business_hours_element.get_attribute("aria-label")
                    file.write(f"營業時間: {business_hours}\n")
                    businesshour = True
                except:
                    businesshour = False
                if not businesshour:
                    try:
                        # 讀取特定店家的營業時間 ex(永林)
                        button = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-item-id="oh"]')
                        button.click()
                        time.sleep(0.5)
                        business_hours_element = driver.find_element(By.XPATH, '//div[@class="t39EBf GUrTXd"]')
                        business_hours = business_hours_element.get_attribute("aria-label")
                        file.write(f"營業時間: {business_hours}\n")
                        businesshour = True
                        time.sleep(0.5)
                        back_window = driver.find_element(By.XPATH, '//button[contains(@class, "VfPpkd-icon-LgbsSe yHy1rc eT1oJ mN1ivc") and @aria-label="返回"]')
                        back_window.click()
                    except:
                        businesshour = False
                if not businesshour:
                    file.write("無提供營業時間\n")

                try:
                    # 讀取外送平台網站
                    address_element = driver.find_element(By.XPATH, '//a[@class="CsEnBe" and @data-tooltip="預訂"]')
                    address = address_element.get_attribute("href")
                    file.write(f"外送平台網站: {address}\n")
                    fooddilivery = True
                except:
                    fooddilivery = False

                if not fooddilivery:
                    try:
                        # 讀取特定店家的外送平台網站 ex(永林)
                        address_element = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-tooltip="預訂"]')
                        address_element.click()
                        time.sleep(0.5)
                        window = driver.find_element(By.XPATH, '//div[@class="m6QErb XiKgde "]')
                        address_elements = window.find_elements(By.XPATH, './/a[@class="CsEnBe"]')
                        for address_element in address_elements:
                            address = address_element.get_attribute("href")
                            file.write(f"外送平台網站: {address}\n")
                        fooddilivery = True
                        time.sleep(0.5)
                        close_window = window.find_element(By.XPATH, '//button[@class="omsYrc"]')
                        close_window.click()
                    except:
                        fooddilivery = False
                if not fooddilivery:
                    file.write("無提供外送平台網站\n")

                try:
                    # 讀取粉專網站
                    address_element = driver.find_element(By.XPATH, '//a[@class="CsEnBe" and @data-tooltip="開啟網站"]')
                    address = address_element.get_attribute("href")
                    file.write(f"粉專網站: {address}\n")
                except:
                    file.write("無提供粉專網站\n")

                try:
                    # 讀取電話號碼
                    phone_element = driver.find_element(By.XPATH, '//button[@class="CsEnBe" and @data-tooltip="複製電話號碼"]')
                    phone = phone_element.get_attribute("aria-label")
                    file.write(phone + "\n")
                except:
                    file.write("無電話號碼\n")
                try:
                    # 下滑頁面
                    scrollable_div = driver.find_element(By.XPATH, '//div[@class="m6QErb DxyBCb kA9KIf dS8AEf XiKgde "]')
                    scroll_div(scrollable_div)
                    button = driver.find_element(By.XPATH, '//div[@class="m6QErb Hk4XGb QoaCgb XiKgde KoSBEe tLjsW "]//div[@class=""]/button[@class="M77dve "]')
                    button.click()
                    time.sleep(0.5)
                    # 讀取標籤
                    tags = driver.find_elements(By.XPATH, '//div[contains(@class, "KNfEk") or contains(@class, "KNfEk lpkcdc")]')
                    for tag in tags:
                        tag_name = tag.find_element(By.XPATH, './/button[@class="e2moi "]').get_attribute("aria-label")
                        file.write(f"標籤: {tag_name}\n")
                    file.write("\n")
                    # 讀取評論
                    comments = driver.find_elements(By.XPATH, '//div[@class="m6QErb XiKgde "]/div[@class="jftiEf fontBodyMedium "]')
                    j=1
                    for comment in comments:
                        shop_star = comment.find_element(By.XPATH, './/span[@class="kvMYJc"]').get_attribute("aria-label")
                        upload_time = comment.find_element(By.XPATH, './/span[@class="rsqaWe"]').text
                        try:
                            # 點擊"查看更多評論"
                            button = comment.find_element(By.XPATH, './/button[@class="w8nwRe kyuRq"]')
                            button.click()
                            time.sleep(0.5)
                        except:
                            # 若無"查看更多評論"按鈕，則pass
                            pass
                        comment_text = comment.find_element(By.XPATH, './/div[@class="MyEned"]/span[@class="wiI7pd"]').text
                        file.write(f"評論 {j}:\n")
                        file.write(f"星級: {shop_star}\n")
                        file.write(f"上傳時間: {upload_time}\n")
                        file.write(f"評論內容: {comment_text}\n")
                        file.write("\n")
                        j+=1
                except:
                    file.write("未搜尋到評論或標籤\n")
                file.write("===============================================================================================================\n")
                if i % 5 == 0:
                    print("已完成", round(i/len(restaurants)*100), "%")
                time.sleep(0.5)
            print("已完成100%")
            print("檔案已儲存為data.txt")
    elif mode == '88':
        print("結束搜尋")
        break
    else:
        print("輸入錯誤")

driver.quit()