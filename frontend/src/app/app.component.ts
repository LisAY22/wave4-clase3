import { Component, inject, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from './product.service';
import { Product } from './models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private api = inject(ProductService);

  // filtros y paginación
  name = '';
  currency = '';
  active: string = ''; // '', 'true', 'false'
  page = 0;
  size = 10;
  sort = 'createdAt,desc';

  // estado
  loading = signal(false);
  error = signal<string | null>(null);
  products = signal<Product[]>([]);
  totalPages = signal(0);
  totalElements = signal(0);
  first = signal(true);
  last = signal(true);

  // formulario simple (crear/editar)
  form: Product = { name: '', price: 0, currency: 'USD', active: true };
  editId: number | null = null;

  constructor() {
    this.load();
  }

  parseActive() {
    if (this.active === '') return undefined;
    return this.active === 'true';
  }

  load() {
    this.loading.set(true);
    this.error.set(null);
    this.api.search({
      name: this.name || undefined,
      currency: this.currency || undefined,
      active: this.parseActive(),
      page: this.page,
      size: this.size,
      sort: this.sort
    }).subscribe({
      next: (res) => {
        this.products.set(res.content);
        this.totalPages.set(res.totalPages);
        this.totalElements.set(res.totalElements);
        this.first.set(res.first);
        this.last.set(res.last);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err?.error?.message || 'Error cargando productos');
        this.loading.set(false);
      }
    });
  }

  resetPageAndLoad() { this.page = 0; this.load(); }
  prevPage() { if (!this.first()) { this.page--; this.load(); } }
  nextPage() { if (!this.last()) { this.page++; this.load(); } }

  // CRUD
  submit() {
    if (this.editId == null) {
      this.api.create(this.form).subscribe({
        next: () => { this.clearForm(); this.resetPageAndLoad(); },
        error: (err) => this.error.set(err?.error?.message || 'Error creando')
      });
    } else {
      this.api.update(this.editId, this.form).subscribe({
        next: () => { this.clearForm(); this.load(); },
        error: (err) => this.error.set(err?.error?.message || 'Error actualizando')
      });
    }
  }

  edit(p: Product) {
    this.editId = p.id!;
    this.form = { name: p.name, price: p.price, currency: p.currency, active: p.active };
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  remove(p: Product) {
    if (!confirm(`¿Eliminar "${p.name}"?`)) return;
    this.api.delete(p.id!).subscribe({
      next: () => this.resetPageAndLoad(),
      error: (err) => this.error.set(err?.error?.message || 'Error eliminando')
    });
  }

  clearForm() {
    this.editId = null;
    this.form = { name: '', price: 0, currency: 'USD', active: true };
  }
}
