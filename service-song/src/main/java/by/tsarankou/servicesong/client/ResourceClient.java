package by.tsarankou.servicesong.client;


import by.tsarankou.servicesong.dto.IdsDTO;

public interface ResourceClient {
    IdsDTO ping(String ids);
}
