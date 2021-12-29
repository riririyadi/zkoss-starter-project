package implement;

import controller.MyBatisConnectionFactory;
//import model.Countries;
import model.Item;

import java.sql.Connection;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;
import model.City;
import model.Company;
import model.Country;
import model.Customer;
import model.DetailSO;
import model.HeaderSO;
import model.MostPopularItem;
import model.Province;
import model.User;
import model.Users;

public class DemoImplement
{

    private SqlSessionFactory sqlSessionFactory;
    private Connection conn;

    public DemoImplement()
    {
        sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
    }

    
    public List<Country> getListCountry() 
    {
        List<Country> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListCountries");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    public Integer onCountTotalCountries() 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountTotalCountries");
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
    
    public Integer onCountCountries(Map map) 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountCountries", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
    
    
      public List<Country> findListCountry(Map map) 
    {
        List<Country> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListCountries", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    
    public Map countries_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.countries_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map countries_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.countries_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map countries_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.countries_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
     public List<MostPopularItem> getListMostPopularItem() 
    {
        List<MostPopularItem> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListMostPopularItem");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    public List<Item> getListItem() 
    {
        List<Item> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListItems");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
        
    public List<Item> findListItem(Map map) 
    {
        List<Item> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListItems", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
      public Integer onCountItems(Map map) 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountItems", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
      
         public Integer onCountTotalItems() 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountTotalItems");
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
    
    public Map items_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.items_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map items_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.items_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map items_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.items_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
    
     public List<Province> getListProvinces() 
    {
        List<Province> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListProvinces");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
      public Integer onCountProvinces(Map map) 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountProvinces", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
      
    public List<Province> findListProvince(Map map) 
    {
        List<Province> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListProvinces", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    
    public Map provinces_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.provinces_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map provinces_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.provinces_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map provinces_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.provinces_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
    
    
     public List<City> getListCities() 
    {
        List<City> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListCities");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
        public Integer onCountCities(Map map) 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountCities", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
      
     
     public List<City> findListCity(Map map) 
    {
        List<City> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListCities", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    public Map cities_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.cities_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map cities_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.cities_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map cities_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.cities_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
     public List<Company> getListCompanies() 
    {
        List<Company> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListCompanies");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
        public Integer onCountCompanies(Map map) 
        {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountCompanies", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
        }
      
    
     public List<Company> findListCompany(Map map) 
    {
        List<Company> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListCompanies", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    } 
     
    public Map companies_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.companies_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map companies_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.companies_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map companies_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.companies_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
    
    public List<Customer> getListCustomers() 
    {
        List<Customer> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListCustomers");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
        public Integer onCountTotalCustomers() 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountTotalCustomers");
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
    
    
     public Integer onCountCustomers(Map map) 
        {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountCustomers", map);
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
        }
      
    
    
    public List<Customer> findListCustomer(Map map) 
    {
        List<Customer> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.findListCustomers", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    } 
     
    
    public Map customers_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.customers_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }

    
    public Map customers_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.customers_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
      
    public Map customers_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.customers_ondelete", map);

        }
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
     return map;
    
    }
    
      public List<HeaderSO> getListHeaderSO() 
    {
        List<HeaderSO> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListHeaderSO");
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
            public Integer onCountTotalHeaderSO() 
    {
        Integer jumlah;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            jumlah = session.selectOne("mapper.onCountTotalHeaderSO");
        } 
        finally 
        {
            session.close();
        }
        return jumlah;
    }
    
    
      
      public List<HeaderSO> getListHeaderSObyFilter(Map map) 
    {
        List<HeaderSO> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListHeaderSObyFilter", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
      
    public Map header_so_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.header_so_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
    
        public Map header_so_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.header_so_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
        
    public Map header_so_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.header_so_ondelete", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
    
    
    public List<DetailSO> getListDetailSO(Map map) 
    {
        List<DetailSO> list;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            list = session.selectList("mapper.getListDetailSO", map);
        } 
        finally 
        {
            session.close();
        }
        return list;
    }
    
    public Map detail_so_oninsert(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.detail_so_oninsert", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
    
    public Map detail_so_onupdate(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.detail_so_onupdate", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
    
        
    public Map detail_so_ondelete(Map map) 
    {
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            session.selectList("mapper.detail_so_ondelete", map);
        } 
        catch (Exception e)
        {
            session.rollback();
        }
        finally 
        {
             session.close();
        }
        return map;
    }
    

    public List<User> login(Map map) 
    {
        List<User> user;
        SqlSession session = sqlSessionFactory.openSession();
        try 
        {
            user = session.selectList("mapper.login", map);
        } 
        finally 
        {
            session.close();
        }
        return user;
    }
    
    
}