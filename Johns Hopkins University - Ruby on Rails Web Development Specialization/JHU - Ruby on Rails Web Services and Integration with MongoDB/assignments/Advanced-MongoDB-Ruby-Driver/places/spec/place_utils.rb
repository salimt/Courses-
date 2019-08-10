module Place_utils

    def address_component_equal?(ac1, ac2) 
      if (ac1.long_name != ac2.long_name)
        return false
      elsif(ac1.short_name != ac2.short_name)
        return false
      elsif(ac1.types.count != ac2.types.count)
        return false
      else
        ac1.types.each { |v|
          if (!ac2.types.include?(v))
            return false
          end
        }
      end
      return true
    end

    def contains_address_component?(arr, ac) 
      arr.each { |ac0| 
        if (address_component_equal?(ac0, ac))
          return true
        end
      }
      return false
    end

    def address_component_match?(arr, field, value)
      arr.each { |ac| 
        if (ac[field] == value)
          return true
        end
      }
      return false
    end
end