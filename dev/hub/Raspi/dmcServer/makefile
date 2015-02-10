########################################################################################################################
# Domocracy
# Created on 2014/Nov/17
########################################################################################################################

DMC_DIR := ../../../..

DMC_LIB_DIR := $(DMC_DIR)/lib/dmcLib
DMC_LIB := $(DMC_LIB_DIR)/bin/linux/libdmclib.a
EXTRA_DEPEND := $(DMC_LIB)
LIB_DIR := -L$(DMC_LIB_DIR)/bin/linux

INCLUDE_FLAGS = -I$(DMC_LIB_DIR)/src

# Define our toolchain
AR := ar
CXX := g++

# Source files
SRC_DIR ?= ./src
CXX_SRC := $(shell find $(SRC_DIR) -name *.cpp)
CXX_OBJ := $(patsubst %.cpp, %.cpp.o, $(CXX_SRC))
OBJ_FILES := $(CXX_OBJ)

# Temporary files
TEMP_FILES := $(OBJ_FILES)

WARNING_FLAGS := -Wall -Werror
INCLUDE_FLAGS +=  -I$(SRC_DIR)
CXX_COMPILE_FLAGS := -std=c++11 -fno-access-control -fno-enforce-eh-specs\
 			-fno-rtti -fno-exceptions -ffunction-sections -fdata-sections
DEBUG_FLAGS := -g
COMMON_C_FLAGS := $(PLATFORM_FLAGS) $(WARNING_FLAGS)\
 			 $(INCLUDE_FLAGS) $(DEBUG_FLAGS) $(OPTIMIZATION_FLAGS)
CC_FLAGS := $(COMMON_C_FLAGS)
CXX_FLAGS := $(COMMON_C_FLAGS) $(CXX_COMPILE_FLAGS)

# Complete configuration
OUT_NAME := $(PRJ_NAME)

BIN_DIR := bin/linux
OUTPUT := $(BIN_DIR)/$(OUT_NAME).hex

BUILD_DIR := build

# -- Rules --
all: $(OUTPUT)

clean:
	rm -f $(OUTPUT) $(TEMP_FILES) $(EXTRA_DEPEND)
	cd $(DMC_LIB_DIR) && $(MAKE) clean
	
rebuild: clean all

run: $(OUTPUT)
	$(OUTPUT)
	
$(BIN_DIR):
	mkdir -p $(BIN_DIR)

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

$(OUTPUT): $(OBJ_FILES) $(BUILD_DIR) $(EXTRA_DEPEND) $(BIN_DIR)
	$(CXX) -o $@ $(OBJ_FILES) $(CXX_FLAGS) $(LIB_DIR) -lbbduino -lc -lm -lc

%.cpp.s: %.cpp
	$(CXX) -S -o $@ $^ $(CXX_FLAGS) $(LIB_DIR)

%.cpp.o: %.cpp
	$(CXX) -c -o $@ $^ $(CXX_FLAGS) $(LIB_DIR) -lc -lm -lc

assembly: (OUT_NAME).S

$(OUT_NAME).S: $(CXX_SRC)
	$(CXX) -S -o $@ $^ $(CXX_FLAGS)

$(DMC_LIB):
	cd $(DMC_LIB_DIR) && $(MAKE) -board=$(board)
	
.PHONY: dwarf dump readelf $(BIN_DIR) $(BUILD_DIR) run clean rebuild $(BBDUINO)
