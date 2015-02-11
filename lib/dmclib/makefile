########################################################################################################################
# BBDuino
# Created on 2015/Feb/07
########################################################################################################################
PRJ_NAME := dmclib

OUT_NAME := lib$(PRJ_NAME).a

AR := ar
CXX := g++

SRC_DIR ?= ./src
CXX_SRC := $(shell find $(SRC_DIR) -name *.cpp)
CXX_OBJ := $(patsubst %.cpp, %.cpp.o, $(CXX_SRC))
OBJ_FILES := $(CXX_OBJ)

# Temporary files
TEMP_FILES := $(OBJ_FILES)

WARNING_FLAGS := -Wall -Werror
INCLUDE_FLAGS +=  -I$(SRC_DIR)
CXX_COMPILE_FLAGS := -std=c++11 -fno-rtti -fno-exceptions
DEBUG_FLAGS := -g
COMMON_C_FLAGS := $(PLATFORM_FLAGS) $(PREPROCESSOR_DEFINITIONS) $(WARNING_FLAGS)\
 			 $(INCLUDE_FLAGS) $(DEBUG_FLAGS) $(OPTIMIZATION_FLAGS)
CC_FLAGS := $(COMMON_C_FLAGS)
CXX_FLAGS := $(COMMON_C_FLAGS) $(CXX_COMPILE_FLAGS)

BIN_DIR := bin/linux
OUTPUT := $(BIN_DIR)/$(OUT_NAME)

# -- Rules --
all: $(OUTPUT)

clean:
	rm -f $(OUTPUT) $(TEMP_FILES) $(ELF)
	
rebuild: clean all

$(OUTPUT): $(CXX_OBJ) $(BIN_DIR)
	$(AR) rcs $(OUTPUT) $(CXX_OBJ)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)
	
run: $(OUTPUT)
	$(AVRDUDE) -p$(MCU) -c$(PROG_PROTOCOL) -b115200 -P$(PORT) -D -Uflash:w:$^:i

%.cpp.s: %.cpp
	$(CXX) -S -o $@ $^ $(CXX_FLAGS) $(LIB_DIR)

%.cpp.o: %.cpp
	$(CXX) -c -o $@ $^ $(CXX_FLAGS) $(LIB_DIR) -lc -lm -lc

.PHONY: clean rebuild $(BBDUINO) $(BIN_DIR)
