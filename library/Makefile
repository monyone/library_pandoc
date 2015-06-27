# Register Suffix for Make
.SUFFIXES: .tex .dvi .pdf .xbb .png .jpg .md .mdc

# Register PHONY target 
.PHONY: fix-inenr-link derive-source build pdf html markdown make-index tex-md-clean clean dist-clean
.DEFAULT_GOAL:= pdf

# Register Variable to Default Value
buildtype := html

# Compile Flag
TEX	= platex
DVIPDFM	= dvipdfmx
DVIPDFMGLAGS = -p a4
EBB	= extractbb
EBB_SUFFIX= .xbb

# Image Suffix Rule
.pdf.xbb:
	$(EBB) $<
.jpg.xbb:
	$(EBB) $<
.png.xbb:
	$(EBB) $<

# LaTeX Compile Suffix Rule
.tex.dvi:
	$(TEX) $<
	$(TEX) $<
	$(TEX) $<
.dvi.pdf:
	$(DVIPDFM) $(DVIPDFMFLAGS) $<

# Markdown Translation Siffix Rule
.mdc.md:
	./paste-source.py $< $@
.md.tex:
	pandoc $< -o $@ --listings	

# Dependence Files
MDC_FILES = $(wildcard */*.mdc)
MD_FILES  = $(MDC_FILES:.mdc=.md)
CONTENT_TEX_FILES = $(MD_FILES:.md=.tex)
SECTION_TEX_FILES = $(subst .,section.tex, $(wildcard */.))
TEX_FILES = $(SECTION_TEX_FILES) $(CONTENT_TEX_FILES)

# Target filename
TARGET = library

# Target build rule for html format
$(TARGET).html: $(TARGET).tex
	pandoc -s --self-contained -r latex $< -t html5 --toc --toc-depth=2 -N -c $(TARGET).css --template=$(TARGET).template.html -H $(TARGET).include_in_header.html -o $@

# Create Section page for subfolder
$(SECTION_TEX_FILES): $(CONTENT_TEX_FILES)
	./make-section.sh $(dir $@)

# allocate Source Code 
derive-source:
	./derive-source.sh

# Fix external link to Internal Link in LaTeX format
fix-inner-link: $(CONTENT_TEX_FILES)
	for file in $(CONTENT_TEX_FILES) ; do \
	  sed -i -e 's/\\href{\.\/library\.html\\#\([^\\}]*\)\}/\\hyperref\[\1\]/g' $$file; \
	done	

build: derive-source $(SECTION_TEX_FILES)
	touch $(TARGET).tex


pdf: build fix-inner-link $(TARGET).pdf

html: tex-md-clean build $(TARGET).html

markdown: tex-md-clean build $(MD_FILES)
	pandoc -s -r latex $(TARGET).tex -t markdown_github -o $(TARGET).md

make-index: html
	cp $(TARGET).html index.html
	sed -i "s/$(TARGET).html/index.html/g" index.html

tex-md-clean:
	@rm -rf */*.tex */*.md	

clean: tex-md-clean
	@rm -rf *.dvi *.toc *.aux *.log *.out */*.tex */*.md */*.java

dist-clean: clean
	@rm -rf $(TARGET).pdf $(TARGET).html $(TARGET).md index.html 
