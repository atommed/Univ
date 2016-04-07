#pragma once

#include <functional_hash>

namespace io {
namespace github {
namespace atommed {
namespace ADS {
namespace hash {
template <typename KeyT, typename ValT> struct Entry {
  KeyT key;
  ValT val;
};
template <typename KeyT, typename ValT,
	 typename hashT = std::hash<KeyT>> class HashTable {
  static constexpr unsigned int initial_data_length = 42;
  using EntryT = Entry<KeyT, ValT>;

public:
  Hash() { data = new EntryT[initial_data_length]; }
  ~Hash() { delete[] data; }

private:
  EntryT *data;
}; // class Hash

} // namespace hash
} // namespace ADS
} // namespace atommed
} // namespace github
} // namespace io
