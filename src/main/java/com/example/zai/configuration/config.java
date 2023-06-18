package com.example.zai.configuration;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.zai.enums.Category;
import com.example.zai.enums.OrderStatus;
import com.example.zai.enums.Roles;
import com.example.zai.models.*;
import com.example.zai.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class config implements WebMvcConfigurer {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final InformationRepository informationRepository;

    private final PasswordEncoder passwordEncoder;


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
//        configuration.setExposedHeaders(Arrays.asList("Content-Type", "Authorization"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


    //Bez tego css sie psuje czasem, wiec lepiej hardcode
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
    }

    @Bean
    public CommandLineRunner initData() {
        return (args) -> {


            Information information2 = Information.builder()
                    .name("Phone")
                    .category("ContactUs")
                    .content("048-123-456-789")
                    .build();

            Information information3 = Information.builder()
                    .name("Email")
                    .category("ContactUs")
                    .content("email@email")
                    .build();

            Information information4 = Information.builder()
                    .name("Address")
                    .category("ContactUs")
                    .content("00-000 City, Street 1")
                    .build();

            Information information1 = Information.builder()
                    .name("About Us")
                    .category("AboutUs")
                    .content("We are a small company that has been operating since 2010. We are constantly developing and expanding our offer. We are open to new ideas and suggestions. We invite you to familiarize yourself with our offer.")
                    .build();

            Information information5 = Information.builder()
                    .name("Privacy Policy")
                    .category("privacyPolicy")
                    .content(
                            "Personal Information We Collect\n" +
                                    "We may collect certain personal information about you when you interact with our Site, including:\n" +
                                    "\n" +
                                    "Name\n" +
                                    "Contact information (such as email address, phone number, and mailing address)\n" +
                                    "Payment information (such as credit card details)\n" +
                                    "Order history and preferences\n" +
                                    "Information provided through customer support inquiries or surveys\n" +
                                    "How We Use Your Personal Information\n" +
                                    "We use the personal information we collect for various purposes, including:\n" +
                                    "\n" +
                                    "Processing and fulfilling your orders\n" +
                                    "Communicating with you regarding your purchases, account, or customer support inquiries\n" +
                                    "Improving and personalizing your shopping experience\n" +
                                    "Sending you marketing communications, if you have opted in to receive them\n" +
                                    "Analyzing and enhancing the security and functionality of our Site\n" +
                                    "Complying with legal obligations\n" +
                                    "Information Sharing\n" +
                                    "We may share your personal information with third parties in the following circumstances:\n" +
                                    "\n" +
                                    "Service Providers: We may engage third-party service providers to assist with various aspects of our business operations, such as payment processing, order fulfillment, and marketing communications. These service providers will have access to your personal information only to the extent necessary to perform their functions.\n" +
                                    "\n" +
                                    "Legal Compliance and Protection: We may disclose your personal information to comply with applicable laws, regulations, legal processes, or governmental requests. We may also disclose your information to protect our rights, property, or safety, or the rights, property, or safety of others.\n" +
                                    "\n" +
                                    "Business Transfers: In the event of a merger, acquisition, or sale of all or a portion of our business or assets, your personal information may be transferred to the acquiring entity.\n" +
                                    "\n" +
                                    "Cookies and Similar Technologies\n" +
                                    "We use cookies and similar technologies to collect information about your browsing activities on our Site. You can manage your cookie preferences through your browser settings.\n" +
                                    "\n" +
                                    "Data Retention\n" +
                                    "We will retain your personal information for as long as necessary to fulfill the purposes outlined in this Privacy Policy, unless a longer retention period is required or permitted by law.\n" +
                                    "\n" +
                                    "Your Rights\n" +
                                    "You may have certain rights regarding your personal information, including the right to access, correct, or delete your information. Please contact us using the information provided below to exercise these rights.\n" +
                                    "\n" +
                                    "Third-Party Websites\n" +
                                    "Our Site may contain links to third-party websites or services. This Privacy Policy does not apply to those third-party websites or services. We recommend reviewing the privacy policies of those websites or services before providing any personal information.\n" +
                                    "\n" +
                                    "Security\n" +
                                    "We take reasonable measures to protect the security of your personal information. However, no method of transmission over the internet or electronic storage is completely secure. Therefore, we cannot guarantee absolute security.\n" +
                                    "\n" +
                                    "Children's Privacy\n" +
                                    "Our Site is not intended for children under the age of 13. We do not knowingly collect personal information from children under the age of 13. If you believe we have collected personal information from a child under the age of 13, please contact us, and we will take appropriate steps to remove the information.\n" +
                                    "\n" +
                                    "Updates to this Privacy Policy\n" +
                                    "We may update this Privacy Policy from time to time. Any changes will be posted on this page, and the \"Last Updated\" date at the top will reflect the effective date of the most recent")
                    .build();

            Information information6 = Information.builder()
                    .name("Orders And Returns")
                    .category("ordersAndReturns")
                    .content("How can I place an order?\n" +
                            "\n" +
                            "To place an order, browse our products and select the item(s) you wish to purchase. Add the selected item(s) to your cart and proceed to the checkout page. Follow the prompts to provide your shipping address, select the preferred shipping method, and complete the payment process. Once your order is confirmed, you will receive an order confirmation email.\n" +
                            "\n\n\nCan I cancel or modify my order?\n" +
                            "\n" +
                            "If you need to cancel or modify your order, please contact our customer support team as soon as possible. We will do our best to accommodate your request if your order has not been processed or shipped yet. However, please note that once an order has been shipped, it cannot be canceled or modified.\n" +
                            "\n\n\nHow can I track my order?\n" +
                            "\n" +
                            "Once your order is shipped, we will provide you with a tracking number and instructions on how to track your package. You can use the tracking number on the respective shipping carrier's website or through our order tracking page to monitor the progress of your shipment.\n" +
                            "\n\n\nWhat is your return policy?\n" +
                            "\n" +
                            "We have a 14 days return policy. If you are not satisfied with your purchase or need to return an item for any reason, please review our Returns Policy for detailed instructions. It outlines the eligibility criteria, return process, and any applicable fees or restrictions.\n" +
                            "\n\n\nCan I return an item if it doesn't fit or if I change my mind?\n" +
                            "\n" +
                            "Yes, you can return an item if it doesn't fit or if you change your mind, subject to our return policy. Please ensure that the item is unused, in its original condition, and with all tags and packaging intact. We may not be able to accept returns for certain items due to hygiene or safety reasons.\n" +
                            "\n\n\nHow do I initiate a return?\n" +
                            "\n" +
                            "To initiate a return, please contact our customer support team or visit our Returns page for instructions. Provide the necessary details, including your order number and the reason for the return. Our team will guide you through the return process and provide you with a return authorization, if applicable.\n" +
                            "\n\n\nAre there any return shipping fees?\n" +
                            "\n" +
                            "Return shipping fees may vary depending on the reason for the return and your location. In some cases, return shipping costs may be the responsibility of the customer, while in other cases, we may provide a prepaid shipping label. Please refer to our Returns Policy for specific details on return shipping fees.\n" +
                            "\n\n\nWhat if I receive a damaged or defective item?\n" +
                            "\n" +
                            "If you receive a damaged or defective item, please contact our customer support team immediately or within 7 days" +
                            " of receiving the order. Provide details and, if possible, include supporting photos or videos. We will assist you with the return process and provide a replacement or refund, as applicable.\n" +
                            "\n\n\nHow long does it take to process a return?\n" +
                            "\n" +
                            "Once we receive your returned item, we will inspect it to ensure it meets the return eligibility criteria. The processing time may vary, but we strive to process returns within 4 weeks. After the return is processed, we will issue a refund or provide a replacement, depending on your preference and the availability of the item.")
                    .build();

            Information information7 = Information.builder()
                    .name("Terms And Conditions")
                    .category("termsAndConditions")
                    .content("Use of the Site\n\n" +
                            "1.1 Eligibility: You must be at least 18 years old and capable of forming a legally binding contract to use the Site and make purchases.\n" +
                            "\n" +
                            "1.2 Account Registration: To access certain features of the Site or make a purchase, you may need to create an account. You are responsible for maintaining the confidentiality of your account information and agree to accept responsibility for all activities that occur under your account.\n" +
                            "\n" +
                            "1.3 Prohibited Activities: You agree not to engage in any activities on the Site that are unlawful, harmful, or infringe upon the rights of others. This includes, but is not limited to, unauthorized access, distribution of harmful content, and spamming.\n" +
                            "\n" +
                            "\n\nProduct Information and Pricing\n\n" +
                            "2.1 Product Descriptions: We make every effort to provide accurate and detailed product descriptions. However, we do not warrant that the product descriptions or any other content on the Site are accurate, complete, reliable, or error-free.\n" +
                            "\n" +
                            "2.2 Pricing: The prices displayed on the Site are in polish zloty - PLN currency and are subject to change without notice. We reserve the right to modify or discontinue any product or service without liability.\n" +
                            "\n" +
                            "\n\nOrders and Payments\n\n" +
                            "3.1 Order Placement: By placing an order on the Site, you are making an offer to purchase the products subject to these Terms. We reserve the right to accept or reject any order in our sole discretion.\n" +
                            "\n" +
                            "3.2 Payment: Payment for products is due at the time of purchase. We accept visa, mastercard, paypal, blik and bitcoin payments and utilize secure payment processing to protect your payment information.\n" +
                            "\n" +
                            "3.3 Order Fulfillment: We will make reasonable efforts to fulfill accepted orders promptly. However, we are not liable for any delays or inability to fulfill orders due to circumstances beyond our control.\n" +
                            "\n" +
                            "\n\nShipping and Returns\n\n" +
                            "4.1 Shipping: We will ship products to the shipping address provided during the order process. Shipping costs and estimated delivery times will be displayed at checkout.\n" +
                            "\n" +
                            "4.2 Returns and Refunds: We have a 14 days return policy for eligible products. Please refer to our Returns Policy for detailed instructions on returning products and requesting refunds.\n" +
                            "\n" +
                            "\n\nIntellectual Property\n\n" +
                            "5.1 Ownership: All intellectual property rights, including trademarks, logos, designs, and content, displayed on the Site are owned by us or our licensors.\n" +
                            "\n" +
                            "5.2 Restricted Use: You may not use, reproduce, modify, or distribute any of our intellectual property without our prior written consent.\n" +
                            "\n" +
                            "\n\nLimitation of Liability\n\n" +
                            "6.1 Disclaimer: The Site and products are provided on an \"as-is\" and \"as available\" basis without warranties of any kind, either expressed or implied.\n" +
                            "\n" +
                            "6.2 Limitation of Liability: To the extent permitted by law, we shall not be liable for any indirect, consequential, incidental, punitive, or special damages arising out of or in connection with your use of the Site or products.\n" +
                            "\n" +
                            "\n\nGoverning Law and Dispute Resolution\n\n" +
                            "7.1 Governing Law: These Terms shall be governed by and construed in accordance with the laws of polish jurisdiction, without regard to its conflict of law provisions.\n" +
                            "\n" +
                            "\n\nSeverability\n\n" +
                            "If any provision of these Terms is found to be invalid or unenforceable, that provision shall be deemed severed from these Terms, and the remaining provisions shall continue to be valid and enforceable.\n" +
                            "\n" +
                            "\n\nModifications to the Terms\n\n" +
                            "We reserve the right to update or modify these Terms at any time without notice. It is your responsibility to review these Terms periodically for any changes.")
                    .build();

            Information information8 = Information.builder()
                    .category("FAQ")
                    .name("How can I place an order?")
                    .content("To place an order, simply browse our products, select the item(s) you wish to purchase, and proceed to the checkout page. Follow the prompts to provide your shipping and payment information. Once your order is confirmed, you will receive an order confirmation email.")
                    .build();

            Information information9 = Information.builder()
                    .category("FAQ")
                    .name("What payment methods do you accept?")
                    .content("We accept Visa, Mastercard, Paypal, BLIK, BTC for payment. You can choose the preferred payment method during the checkout process.")
                    .build();

            Information information10 = Information.builder()
                    .category("FAQ")
                    .name("How long does shipping take?")
                    .content("Shipping times vary depending on your location and the selected shipping method. Typically, orders are processed within 1 day and delivered within 3 days. You can find more details about shipping in our Shipping Policy.")
                    .build();

            Information information11 = Information.builder()
                    .category("FAQ")
                    .name("Can I track my order?")
                    .content("Yes, you can track your order. Once your order is shipped, we will provide you with a tracking number and instructions on how to track your package.")
                    .build();

            Information information12 = Information.builder()
                    .category("FAQ")
                    .name("What is your return policy?")
                    .content("We have a 14 days return policy. If you are not satisfied with your purchase, you may return the item(s) within 14 days of receiving them. Please review our Returns Policy for more information on the return process and eligibility.")
                    .build();

            Information information13 = Information.builder()
                    .category("FAQ")
                    .name("How can I contact customer support?")
                    .content("For any questions, concerns, or assistance, you can reach our customer support team by our mail. We strive to respond promptly to all inquiries.")
                    .build();

            Information information14 = Information.builder()
                    .category("FAQ")
                    .name("Are there any discounts or promotions available?")
                    .content("We occasionally run special promotions and discounts. To stay updated on our latest offers, you can subscribe to our newsletter or follow us on social media platforms for announcements.")
                    .build();

            Information information15 = Information.builder()
                    .category("FAQ")
                    .name("Can I cancel or modify my order?")
                    .content("If you need to cancel or modify your order, please contact our customer support team as soon as possible. We will do our best to accommodate your request if your order has not been shipped yet.")
                    .build();

            Information information16 = Information.builder()
                    .category("FAQ")
                    .name("What if the item I received is damaged or defective?")
                    .content("In the event that you receive a damaged or defective item, please contact our customer support within 7 days of receiving the order. We will assist you with the return process and provide a replacement or refund, as applicable.")
                    .build();


            informationRepository.save(information1);
            informationRepository.save(information2);
            informationRepository.save(information3);
            informationRepository.save(information4);
            informationRepository.save(information5);
            informationRepository.save(information6);
            informationRepository.save(information7);
            informationRepository.save(information8);
            informationRepository.save(information9);
            informationRepository.save(information10);
            informationRepository.save(information11);
            informationRepository.save(information12);
            informationRepository.save(information13);
            informationRepository.save(information14);
            informationRepository.save(information15);
            informationRepository.save(information16);


            Product product1 = Product.builder()
                    .name("Laptop with INTEL CPU")
                    .price(BigDecimal.valueOf(3000.00))
                    .category(Category.LAPTOPS)
                    .image("product01.png")
                    .isAvailable(true)
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .build();
            Product product2 = Product.builder()
                    .name("Great Headphones")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(250.00))
                    .category(Category.HEADPHONES)
                    .image("product02.png")
                    .isAvailable(true)
                    .build();
            Product product3 = Product.builder()
                    .name("Laptop with AMD CPU")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(2000.00))
                    .category(Category.LAPTOPS)
                    .image("product03.png")
                    .isAvailable(true)
                    .build();
            Product product4 = Product.builder()
                    .name("Tablet with Android OS")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(1000.00))
                    .category(Category.TABLETS)
                    .image("product04.png")
                    .isAvailable(true)
                    .build();
            Product product5 = Product.builder()
                    .name("Amazing Headphones")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(100.00))
                    .category(Category.HEADPHONES)
                    .image("product05.png")
                    .isAvailable(true)
                    .build();
            Product product6 = Product.builder()
                    .name("Laptop with 64GB RAM")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(5000.00))
                    .category(Category.LAPTOPS)
                    .image("product06.png")
                    .isAvailable(true)
                    .build();
            Product product7 = Product.builder()
                    .name("Camera with 4K Video Recording")
                    .description("Introducing the ultimate companion for modern-day multitasking and digital versatility: the laptop. Engineered with cutting-edge technology and sleek aesthetics, this portable powerhouse redefines productivity on the go. Its robust performance, fueled by a high-speed processor and ample RAM, effortlessly handles demanding tasks, from graphic design to data analysis. The vibrant display, accentuated by crisp visuals and immersive colors.css, breathes life into movies, presentations, and creative projects. With a spacious and responsive keyboard, precise trackpad, and an array of connectivity options, this laptop ensures seamless navigation and effortless connectivity. Designed for professionals, students, and tech enthusiasts alike, this laptop is a gateway to endless possibilities, empowering users to conquer their digital endeavors with style and efficiency.")
                    .price(BigDecimal.valueOf(4000.00))
                    .category(Category.CAMERAS)
                    .image("product07.png")
                    .isAvailable(true)
                    .build();
            Product product8 = Product.builder()
                    .name("Laptop with WINDOWS")
                    .description("jajco")
                    .price(BigDecimal.valueOf(5000.00))
                    .category(Category.LAPTOPS)
                    .image("product08.png")
                    .isAvailable(true)
                    .build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
            productRepository.save(product7);
            productRepository.save(product8);


            Product product11 = Product.builder()
                    .name("Laptop with INTEL CPU")
                    .price(BigDecimal.valueOf(3000.00))
                    .category(Category.LAPTOPS)
                    .image("product01.png")
                    .isAvailable(true)
                    .description("bułka")
                    .build();
            Product product12 = Product.builder()
                    .name("Great Headphones")
                    .description("bułka")
                    .price(BigDecimal.valueOf(250.00))
                    .category(Category.HEADPHONES)
                    .image("product02.png")
                    .isAvailable(true)
                    .build();
            Product product13 = Product.builder()
                    .name("Laptop with AMD CPU")
                    .description("bułka")
                    .price(BigDecimal.valueOf(2000.00))
                    .category(Category.LAPTOPS)
                    .image("product03.png")
                    .isAvailable(true)
                    .build();
            Product product14 = Product.builder()
                    .name("Tablet with Android OS")
                    .description("bułka")
                    .price(BigDecimal.valueOf(1000.00))
                    .category(Category.TABLETS)
                    .image("product04.png")
                    .isAvailable(true)
                    .build();
            Product product15 = Product.builder()
                    .name("Amazing Headphones")
                    .description("bułka")
                    .price(BigDecimal.valueOf(100.00))
                    .category(Category.HEADPHONES)
                    .image("product05.png")
                    .isAvailable(true)
                    .build();
            Product product16 = Product.builder()
                    .name("Laptop with 64GB RAM")
                    .description("bułka")
                    .price(BigDecimal.valueOf(5000.00))
                    .category(Category.LAPTOPS)
                    .image("product06.png")
                    .isAvailable(true)
                    .build();
            Product product17 = Product.builder()
                    .name("Camera with 4K Video Recording")
                    .description("bułka")
                    .price(BigDecimal.valueOf(4000.00))
                    .category(Category.CAMERAS)
                    .image("product07.png")
                    .isAvailable(true)
                    .build();
            Product product18 = Product.builder()
                    .name("Laptop with WINDOWS")
                    .description("bułka")
                    .price(BigDecimal.valueOf(5000.00))
                    .category(Category.LAPTOPS)
                    .image("product08.png")
                    .isAvailable(true)
                    .build();

            productRepository.save(product11);
            productRepository.save(product12);
            productRepository.save(product13);
            productRepository.save(product14);
            productRepository.save(product15);
            productRepository.save(product16);
            productRepository.save(product17);
            productRepository.save(product18);


            User user1 = User.builder()
                    .email("jajco")
                    .username("jajco")
                    .password(String.valueOf(passwordEncoder.encode("jajco")))
                    .role(Roles.ROLE_ADMIN)
                    .build();
            userRepository.save(user1);

            Order order1 = Order.builder()
                    .user(user1)
                    .status(OrderStatus.SHOPPING_CART)
                    .orderProductsList(new ArrayList<>())
                    .build();

            OrderProduct orderProduct1 = OrderProduct.builder()
                    .product(product1)
                    .quantity(1)
                    .user(user1)
                    .build();

            orderProductRepository.save(orderProduct1);

            OrderProduct orderProduct2 = OrderProduct.builder()
                    .product(product2)
                    .quantity(1)
                    .user(user1)
                    .build();

            orderProductRepository.save(orderProduct2);

            order1.addOrderProduct(orderProduct2);

            order1.addOrderProduct(orderProduct1);

            orderRepository.save(order1);

            Order order2 = Order.builder()
                    .user(user1)
                    .status(OrderStatus.WHISHLIST)
                    .orderProductsList(new ArrayList<>())
                    .build();


//            order2.addOrderProduct(orderProduct2);
//
//            order2.addOrderProduct(orderProduct1);

            orderRepository.save(order2);

        };
    }


}
