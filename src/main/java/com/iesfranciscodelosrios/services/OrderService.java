package com.iesfranciscodelosrios.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.iesfranciscodelosrios.model.AndroidToken;
import com.iesfranciscodelosrios.model.Document;
import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.repositories.OrderRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DiscountService discountService;
    @Autowired
    UserService userService;
    @Autowired
    DocumentService documentService;
    @Autowired
    FileService fileUploadService;
    @Autowired
    GoogleDriveService googleDriveService;
    @Autowired
    NotificationService notificationService;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public List<Order> getAllOrder() {
        List<Order> orders = orderRepository.getAllOrders();

        return orders;
    }

    public Order getOrderById(Long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            logger.info("El pedido con id " + id + " no existe");
            throw new Exception("El pedido con id " + id + " no existe");
        }
    }

    public List<Order> getOrdersByUser(Long id_user) throws Exception {
        List<Order> orders = orderRepository.getOrdersByUser(id_user);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            logger.info("El usuario no tiene pedidos");
            return new ArrayList<Order>();
        }
    }

    public Order createOrder(Order order) throws Exception {
        if (order.getId() == null) {
            order.setId(-1L);
        }

        Optional<Order> o = orderRepository.findById(order.getId());

        if (o.isEmpty()) {
            /**
             * Compruebo que el usuario pasado por JSON exista en la base de datos y me lo traigo ya persistido
             */
            User u1 = order.getUser();

            try {
                User u2 = userService.findUserByMail(u1.getMail());

                if (u1.getMail().equals(u2.getMail())) {
                    try {
                        List<Document> orderdocuments = order.getDocuments();
                        order.setUser(u2);
                        order.setDocuments(new ArrayList<>());
                        order.setDiscounts(discountService.getAllDiscounts());
                        order = orderRepository.save(order);
                        try {
                            documentService.saveDocuments(orderdocuments, order);
                        } catch (Exception e) {
                            throw e;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return order;
                } else {
                    logger.info("El correo del usuario del pedido no concuerda con el correo del usuario de la base de datos");
                    throw new Exception("El correo del usuario del pedido no concuerda con el correo del usuario de la base de datos");
                }
            } catch (Exception e) {
                logger.info("El usuario del pedido no existe en la base de datos");
                throw new Exception("El usuario del pedido no existe en la base de datos");
            }
        } else {
            logger.info("El pedido que intenta crear ya existe en la base de datos");
            throw new Exception("El pedido que intenta crear ya existe en la base de datos");
        }
    }

    public Map<String, List<String>> uploadOrderFiles(List<MultipartFile> multipartFiles, String userName, String userMail) throws Exception {
        try {
            List<File> fileList = fileUploadService.uploadToLocal(multipartFiles);

            LinkedHashMap<String, List<String>> webLinks = googleDriveService.createOrderFolder("Pedido de " + userName, fileList, userMail);

            fileUploadService.flushTmp();

            return webLinks;
        } catch (Exception e) {
            logger.info("Error al subir los archivos a google drive");
            throw e;
        }
    }

    public Order updateOrder(Order order) throws Exception {
        try {
            Optional<Order> o = orderRepository.findById(order.getId());
            if (o.isPresent()) {
                Order updatedOrder = o.get();
                updatedOrder.setPayed(order.isPayed());
                updatedOrder.setPickedUp(order.isPickedUp());
                updatedOrder.setReady(order.isReady());
                if (order.isPayed() || order.isPickedUp() || order.isReady()) {
                    String body = null;
                    if (order.isPayed()) {
                        body = "El pedido está pagado";
                    }
                    if (order.isReady()) {
                        if (body == null) {
                            body = "El pedido está listo";
                        } else {
                            body = body.concat(" y está listo");
                        }
                    }
                    if (order.isPickedUp()) {
                        if (body == null) {
                            body = "El pedido está recogido";
                        } else {
                            body = body.concat(" y está recogido");
                        }
                    }
                    if(body != null) {
                        for (AndroidToken at : updatedOrder.getUser().getAndroidTokens()) {
                            notificationService.sendNotification(Map.of("orderid", o.get().getId()+""),"Tu pedido " + o.get().getId() + " ha actualizado su estado", "Ahora está: " + body, at.getToken());
                        }
                    }
                }
                orderRepository.save(updatedOrder);

                return updatedOrder;
            } else {
                logger.info("El pedido que intenta actualizar no existe en la base de datos");
                throw new Exception("El pedido que intenta actualizar no existe en la base de datos");
            }
        } catch (IllegalArgumentException e) {
            logger.info("Error: La id del pedido que se intenta actualizar es null");
            throw new Exception("Error: La id del pedido que se intenta actualizar es null");
        }
    }

    public void deleteOrderById(Long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            logger.info("El pedidio no existe");
            throw new Exception("El pedido no existe");
        }
    }
}
